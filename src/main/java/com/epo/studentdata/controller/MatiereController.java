package com.epo.studentdata.controller;

import com.epo.studentdata.model.Matiere;
import com.epo.studentdata.repository.MatiereRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/matieres")
public class MatiereController {

    private final MatiereRepository matiereRepository;

    public MatiereController(MatiereRepository matiereRepository) {
        this.matiereRepository = matiereRepository;
    }

    @GetMapping
    public List<Matiere> getAllMatieres() {
        return matiereRepository.findAll();
    }

    @PostMapping
    public Matiere createMatiere(@RequestBody Matiere matiere) {
        return matiereRepository.save(matiere);
    }
}
