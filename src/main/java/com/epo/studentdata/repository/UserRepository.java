package com.epo.studentdata.repository;

import com.epo.studentdata.model.User;
import com.epo.studentdata.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByMatricule(String matricule);
    List<User> findByRole(Role role);
    List<User> findByActifTrue();
}