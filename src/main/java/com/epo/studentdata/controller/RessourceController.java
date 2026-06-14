package com.epo.studentdata.controller;

import com.epo.studentdata.model.Ressource;
import com.epo.studentdata.model.Filiere;
import com.epo.studentdata.model.Matiere;
import com.epo.studentdata.model.TypeRessource;
import com.epo.studentdata.service.IRessourceService;
import com.epo.studentdata.repository.FiliereRepository;
import com.epo.studentdata.repository.MatiereRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/ressources")
public class RessourceController {

    private final IRessourceService ressourceService;
    private final FiliereRepository filiereRepository;
    private final MatiereRepository matiereRepository;

    public RessourceController(IRessourceService ressourceService, FiliereRepository filiereRepository, MatiereRepository matiereRepository) {
        this.ressourceService = ressourceService;
        this.filiereRepository = filiereRepository;
        this.matiereRepository = matiereRepository;
    }

    @GetMapping("/recherche")
    public ResponseEntity<List<Ressource>> rechercherComplexe(
            @RequestParam(required = false) Long matiereId,
            @RequestParam(required = false) String anneeAcademique,
            @RequestParam(required = false) Integer semestre,
            @RequestParam(required = false) TypeRessource type) {
        Matiere matiere = null;
        if (matiereId != null) {
            matiere = matiereRepository.findById(matiereId).orElse(null);
        }
        List<Ressource> ressources = ressourceService.searchRessourcesComplexe(matiere, anneeAcademique, semestre, type);
        return ResponseEntity.ok(ressources);
    }

    @PostMapping("/soumettre")
    public ResponseEntity<Ressource> soumettreRessource(@RequestBody Ressource ressource,
                                                        @RequestParam Long auteurId,
                                                        @RequestParam(required = false) Long matiereId,
                                                        @RequestParam(required = false) String matiereNom) {
        Ressource saved = ressourceService.soumettreRessource(ressource, auteurId, matiereId, matiereNom);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/mes-soumissions/{auteurId}")
    public ResponseEntity<List<Ressource>> getMesSoumissions(@PathVariable Long auteurId) {
        List<Ressource> ressources = ressourceService.getMyRessources(auteurId);
        return ResponseEntity.ok(ressources);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ressource> getRessourceById(@PathVariable Long id) {
        Ressource ressource = ressourceService.getRessourceById(id);
        return ResponseEntity.ok(ressource);
    }
}