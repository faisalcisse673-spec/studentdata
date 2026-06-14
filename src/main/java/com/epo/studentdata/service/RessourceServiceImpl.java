package com.epo.studentdata.service;

import com.epo.studentdata.model.Ressource;
import com.epo.studentdata.model.Filiere;
import com.epo.studentdata.model.TypeRessource;
import com.epo.studentdata.model.StatutRessource;
import com.epo.studentdata.model.User;
import com.epo.studentdata.model.Matiere;
import com.epo.studentdata.model.Notification;
import com.epo.studentdata.repository.RessourceRepository;
import com.epo.studentdata.repository.UserRepository;
import com.epo.studentdata.repository.FiliereRepository;
import com.epo.studentdata.repository.MatiereRepository;
import com.epo.studentdata.repository.NotificationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class RessourceServiceImpl implements IRessourceService {

    private final RessourceRepository ressourceRepository;
    private final UserRepository userRepository;
    private final FiliereRepository filiereRepository;
    private final MatiereRepository matiereRepository;
    private final NotificationRepository notificationRepository;

    public RessourceServiceImpl(RessourceRepository ressourceRepository,
                                UserRepository userRepository,
                                FiliereRepository filiereRepository,
                                MatiereRepository matiereRepository,
                                NotificationRepository notificationRepository) {
        this.ressourceRepository = ressourceRepository;
        this.userRepository = userRepository;
        this.filiereRepository = filiereRepository;
        this.matiereRepository = matiereRepository;
        this.notificationRepository = notificationRepository;
    }

    @Override
    public List<Ressource> getRessourcesApprouveesByFiliere(Filiere filiere) {
        return ressourceRepository.findByFiliereAndStatutOrderByDateUploadDesc(filiere, StatutRessource.APPROUVEE);
    }

    @Override
    public List<Ressource> getCorrectionsApprouveesByFiliere(Filiere filiere) {
        return ressourceRepository.findByFiliereAndTypeAndStatut(filiere, TypeRessource.CORRECTION, StatutRessource.APPROUVEE);
    }

    @Override
    public List<Ressource> searchRessourcesByTitre(String titre) {
        return ressourceRepository.findByTitreContainingIgnoreCaseAndStatut(titre, StatutRessource.APPROUVEE);
    }

    @Override
    public List<Ressource> searchRessourcesComplexe(Matiere matiere, String anneeAcademique, Integer semestre, TypeRessource type) {
        // Pour faire simple sans CriteriaBuilder, on récupère par Matiere et on filtre en Java.
        List<Ressource> ressources;
        if (matiere != null) {
            ressources = ressourceRepository.findByMatiereAndStatutOrderByDatePublicationDesc(matiere, StatutRessource.APPROUVEE);
        } else {
            ressources = ressourceRepository.findByStatutOrderByDatePublicationDesc(StatutRessource.APPROUVEE);
        }
        if (anneeAcademique != null && !anneeAcademique.isEmpty()) {
            ressources.removeIf(r -> !anneeAcademique.equals(r.getAnneeAcademique()));
        }
        if (semestre != null) {
            ressources.removeIf(r -> !semestre.equals(r.getSemestre()));
        }
        if (type != null) {
            ressources.removeIf(r -> !type.equals(r.getType()));
        }
        return ressources;
    }

    @Override
    public Ressource soumettreRessource(Ressource ressource, Long auteurId, Long matiereId, String matiereNom) {
        User auteur = userRepository.findById(auteurId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        ressource.setAuteur(auteur);
        ressource.setStatut(StatutRessource.EN_ATTENTE);
        ressource.setDateUpload(LocalDateTime.now());
        
        if (matiereId != null) {
            Matiere matiere = matiereRepository.findById(matiereId)
                    .orElseThrow(() -> new RuntimeException("Matière non trouvée"));
            ressource.setMatiere(matiere);
        } else if (matiereNom != null && !matiereNom.trim().isEmpty()) {
            Matiere matiere = matiereRepository.findByNomIgnoreCase(matiereNom.trim())
                    .orElseGet(() -> {
                        Matiere newMatiere = new Matiere();
                        newMatiere.setNom(matiereNom.trim());
                        return matiereRepository.save(newMatiere);
                    });
            ressource.setMatiere(matiere);
        }

        if (ressource.getFiliere() != null && ressource.getFiliere().getId() != null) {
            Filiere filiere = filiereRepository.findById(ressource.getFiliere().getId())
                    .orElseThrow(() -> new RuntimeException("Filière non trouvée"));
            ressource.setFiliere(filiere);
        }

        ressource.setAuteur(auteur);
        ressource.setStatut(StatutRessource.EN_ATTENTE);
        ressource.setDateUpload(LocalDateTime.now());

        Ressource saved = ressourceRepository.save(ressource);

        Notification notif = new Notification(auteur, "Votre document '" + ressource.getTitre() + "' a été soumis et est en attente de validation.", "SOUMISSION");
        notificationRepository.save(notif);

        return saved;
    }

    @Override
    public List<Ressource> getMyRessources(Long auteurId) {
        User auteur = userRepository.findById(auteurId).orElseThrow(() -> new RuntimeException("Auteur non trouvé"));
        return ressourceRepository.findByAuteurOrderByDateUploadDesc(auteur);
    }

    @Override
    public List<Ressource> getRessourcesEnAttente() {
        return ressourceRepository.findByStatutOrderByDateUploadAsc(StatutRessource.EN_ATTENTE);
    }

    @Override
    public Ressource validerRessource(Long ressourceId, Long valideurId) {
        Ressource ressource = ressourceRepository.findById(ressourceId)
                .orElseThrow(() -> new RuntimeException("Ressource non trouvée"));
        User valideur = userRepository.findById(valideurId)
                .orElseThrow(() -> new RuntimeException("Valideur non trouvé"));
        ressource.setStatut(StatutRessource.APPROUVEE);
        ressource.setValideur(valideur);
        ressource.setDatePublication(LocalDateTime.now());
        
        Notification notif = new Notification(ressource.getAuteur(), "Votre document '" + ressource.getTitre() + "' a été approuvé et publié.", "VALIDATION_APPROUVEE");
        notificationRepository.save(notif);
        
        return ressourceRepository.save(ressource);
    }

    @Override
    public Ressource rejeterRessource(Long ressourceId, Long valideurId, String commentaire) {
        Ressource ressource = ressourceRepository.findById(ressourceId)
                .orElseThrow(() -> new RuntimeException("Ressource non trouvée"));
        User valideur = userRepository.findById(valideurId)
                .orElseThrow(() -> new RuntimeException("Valideur non trouvé"));
        ressource.setStatut(StatutRessource.REJETEE);
        ressource.setCommentaireRejet(commentaire);
        ressource.setValideur(valideur);
        
        Notification notif = new Notification(ressource.getAuteur(), "Votre document '" + ressource.getTitre() + "' a été rejeté. Motif : " + commentaire, "VALIDATION_REJETEE");
        notificationRepository.save(notif);
        
        return ressourceRepository.save(ressource);
    }

    @Override
    public Ressource demanderRevision(Long ressourceId, Long valideurId, String commentaire) {
        Ressource ressource = ressourceRepository.findById(ressourceId)
                .orElseThrow(() -> new RuntimeException("Ressource non trouvée"));
        User valideur = userRepository.findById(valideurId)
                .orElseThrow(() -> new RuntimeException("Valideur non trouvé"));
        ressource.setStatut(StatutRessource.EN_REVISION);
        ressource.setCommentaireRejet(commentaire);
        ressource.setValideur(valideur);
        
        Notification notif = new Notification(ressource.getAuteur(), "Une révision est demandée pour votre document '" + ressource.getTitre() + "'. Commentaires : " + commentaire, "VALIDATION_REVISION");
        notificationRepository.save(notif);
        
        return ressourceRepository.save(ressource);
    }

    @Override
    public Ressource getRessourceById(Long id) {
        return ressourceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ressource non trouvée"));
    }
}