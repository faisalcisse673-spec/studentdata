package com.epo.studentdata.controller;

import com.epo.studentdata.model.Filiere;
import com.epo.studentdata.repository.FiliereRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/filieres")
public class FiliereController {

    private final FiliereRepository filiereRepository;

    public FiliereController(FiliereRepository filiereRepository) {
        this.filiereRepository = filiereRepository;
        System.out.println("✅ FiliereController chargé");
    }

    @GetMapping
    public List<Filiere> getAllFilieres() {
        System.out.println("📡 GET /filieres");
        return filiereRepository.findAll();
    }

    @GetMapping("/{id}")
    public Filiere getFiliereById(@PathVariable Long id) {
        return filiereRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Filière non trouvée"));
    }
}