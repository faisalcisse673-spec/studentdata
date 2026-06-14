package com.epo.studentdata.controller;

import com.epo.studentdata.model.Notification;
import com.epo.studentdata.model.User;
import com.epo.studentdata.repository.NotificationRepository;
import com.epo.studentdata.service.IUserService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationRepository notificationRepository;
    private final IUserService userService;

    public NotificationController(NotificationRepository notificationRepository, IUserService userService) {
        this.notificationRepository = notificationRepository;
        this.userService = userService;
    }

    @GetMapping("/user/{userId}")
    public List<Notification> getUserNotifications(@PathVariable Long userId) {
        User user = userService.getUserById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return notificationRepository.findByUtilisateurOrderByDateCreationDesc(user);
    }

    @PutMapping("/{id}/lu")
    public Notification markAsRead(@PathVariable Long id) {
        Notification notif = notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification non trouvée"));
        notif.setLu(true);
        return notificationRepository.save(notif);
    }
}
