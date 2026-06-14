package com.epo.studentdata.repository;

import com.epo.studentdata.model.Notification;
import com.epo.studentdata.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUtilisateurOrderByDateCreationDesc(User utilisateur);
    List<Notification> findByUtilisateurAndLuFalseOrderByDateCreationDesc(User utilisateur);
}
