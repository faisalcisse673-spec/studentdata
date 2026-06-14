package com.epo.studentdata.rmi;

import com.epo.studentdata.model.Ressource;
import com.epo.studentdata.model.Filiere;
import com.epo.studentdata.service.IRessourceService;
import com.epo.studentdata.repository.FiliereRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

@Component
public class RessourceRemoteImpl extends UnicastRemoteObject implements IRessourceRemote {

    @Autowired
    private IRessourceService ressourceService;

    @Autowired
    private FiliereRepository filiereRepository;

    // Constructeur obligatoire (throws RemoteException)
    public RessourceRemoteImpl() throws RemoteException {
        super();
    }

    @Override
    public List<Ressource> getRessourcesApprouveesByFiliere(String filiereNom) throws RemoteException {
        Filiere filiere = filiereRepository.findByNom(filiereNom)
                .orElseThrow(() -> new RemoteException("Filière non trouvée : " + filiereNom));
        return ressourceService.getRessourcesApprouveesByFiliere(filiere);
    }

    @Override
    public List<Ressource> getCorrectionsApprouveesByFiliere(String filiereNom) throws RemoteException {
        Filiere filiere = filiereRepository.findByNom(filiereNom)
                .orElseThrow(() -> new RemoteException("Filière non trouvée : " + filiereNom));
        return ressourceService.getCorrectionsApprouveesByFiliere(filiere);
    }

    @Override
    public List<Ressource> searchRessourcesByTitre(String titre) throws RemoteException {
        return ressourceService.searchRessourcesByTitre(titre);
    }

    @Override
    public Ressource soumettreRessource(Ressource ressource, Long auteurId, Long matiereId) throws RemoteException {
        return ressourceService.soumettreRessource(ressource, auteurId, matiereId, null);
    }

    @Override
    public List<Ressource> getRessourcesEnAttente() throws RemoteException {
        return ressourceService.getRessourcesEnAttente();
    }

    @Override
    public Ressource validerRessource(Long ressourceId, Long valideurId) throws RemoteException {
        return ressourceService.validerRessource(ressourceId, valideurId);
    }

    @Override
    public Ressource rejeterRessource(Long ressourceId, Long valideurId, String commentaire) throws RemoteException {
        return ressourceService.rejeterRessource(ressourceId, valideurId, commentaire);
    }

    @Override
    public Ressource getRessourceById(Long id) throws RemoteException {
        return ressourceService.getRessourceById(id);
    }

    @Override
    public List<Filiere> getAllFilieres() throws RemoteException {
        return filiereRepository.findAll();
    }
}