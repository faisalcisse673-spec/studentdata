package com.epo.studentdata.rmi;

import com.epo.studentdata.model.Ressource;
import com.epo.studentdata.model.Filiere;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IRessourceRemote extends Remote {

    // Recherche de ressources validées par filière
    List<Ressource> getRessourcesApprouveesByFiliere(String filiereNom) throws RemoteException;

    // Recherche de corrections par filière
    List<Ressource> getCorrectionsApprouveesByFiliere(String filiereNom) throws RemoteException;

    // Recherche par titre
    List<Ressource> searchRessourcesByTitre(String titre) throws RemoteException;

    // Soumettre une ressource
    Ressource soumettreRessource(Ressource ressource, Long auteurId, Long matiereId) throws RemoteException;

    // Admin : obtenir les ressources en attente
    List<Ressource> getRessourcesEnAttente() throws RemoteException;

    // Admin : valider une ressource
    Ressource validerRessource(Long ressourceId, Long valideurId) throws RemoteException;

    // Admin : rejeter une ressource
    Ressource rejeterRessource(Long ressourceId, Long valideurId, String commentaire) throws RemoteException;

    // Récupérer une ressource par son ID
    Ressource getRessourceById(Long id) throws RemoteException;

    // Récupérer toutes les filières
    List<Filiere> getAllFilieres() throws RemoteException;
}