package com.epo.studentdata.repository;

import com.epo.studentdata.model.Matiere;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MatiereRepository extends JpaRepository<Matiere, Long> {
    Optional<Matiere> findByNom(String nom);
    Optional<Matiere> findByNomIgnoreCase(String nom);
}
