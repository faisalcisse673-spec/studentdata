package com.epo.studentdata.repository;

import com.epo.studentdata.model.Ressource;
import com.epo.studentdata.model.Filiere;
import com.epo.studentdata.model.Matiere;
import com.epo.studentdata.model.User;
import com.epo.studentdata.model.TypeRessource;
import com.epo.studentdata.model.StatutRessource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RessourceRepository extends JpaRepository<Ressource, Long> {
    List<Ressource> findByFiliereAndStatut(Filiere filiere, StatutRessource statut);
    List<Ressource> findByFiliereAndTypeAndStatut(Filiere filiere, TypeRessource type, StatutRessource statut);
    List<Ressource> findByStatutOrderByDateUploadAsc(StatutRessource statut);
    List<Ressource> findByAuteurIdAndStatut(Long auteurId, StatutRessource statut);
    List<Ressource> findByAuteurOrderByDateUploadDesc(User auteur);
    List<Ressource> findByFiliereAndStatutOrderByDateUploadDesc(Filiere filiere, StatutRessource statut);
    List<Ressource> findByMatiereAndStatutOrderByDatePublicationDesc(Matiere matiere, StatutRessource statut);
    List<Ressource> findByStatutOrderByDatePublicationDesc(StatutRessource statut);
    List<Ressource> findByTitreContainingIgnoreCaseAndStatut(String titre, StatutRessource statut);
}