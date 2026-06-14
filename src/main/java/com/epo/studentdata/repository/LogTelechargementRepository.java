package com.epo.studentdata.repository;

import com.epo.studentdata.model.LogTelechargement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogTelechargementRepository extends JpaRepository<LogTelechargement, Long> {
}
