package com.epo.studentdata.service;

import com.epo.studentdata.model.Ressource;
import com.epo.studentdata.model.Filiere;
import com.epo.studentdata.model.Matiere;
import com.epo.studentdata.model.TypeRessource;
import java.util.List;

public interface IRessourceService {
    List<Ressource> getRessourcesApprouveesByFiliere(Filiere filiere);
    List<Ressource> getCorrectionsApprouveesByFiliere(Filiere filiere);
    List<Ressource> searchRessourcesByTitre(String titre);
    List<Ressource> searchRessourcesComplexe(Matiere matiere, String anneeAcademique, Integer semestre, TypeRessource type);
    Ressource soumettreRessource(Ressource ressource, Long auteurId, Long matiereId, String matiereNom);
    List<Ressource> getMyRessources(Long auteurId);
    List<Ressource> getRessourcesEnAttente();
    Ressource validerRessource(Long ressourceId, Long valideurId);
    Ressource rejeterRessource(Long ressourceId, Long valideurId, String commentaire);
    Ressource demanderRevision(Long ressourceId, Long valideurId, String commentaire);
    Ressource getRessourceById(Long id);
}