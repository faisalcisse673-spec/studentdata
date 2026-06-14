package com.epo.studentdata.controller;

import com.epo.studentdata.model.Ressource;
import com.epo.studentdata.model.User;
import com.epo.studentdata.model.Role;
import com.epo.studentdata.service.IRessourceService;
import com.epo.studentdata.service.IUserService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final IRessourceService ressourceService;
    private final IUserService userService;

    public AdminController(IRessourceService ressourceService, IUserService userService) {
        this.ressourceService = ressourceService;
        this.userService = userService;
    }

    @GetMapping("/ressources/en-attente")
    public List<Ressource> getRessourcesEnAttente() {
        return ressourceService.getRessourcesEnAttente();
    }

    @PutMapping("/ressources/{id}/valider")
    public Ressource validerRessource(@PathVariable Long id, @RequestParam Long valideurId) {
        return ressourceService.validerRessource(id, valideurId);
    }

    @PutMapping("/ressources/{id}/rejeter")
    public Ressource rejeterRessource(@PathVariable Long id, @RequestParam Long valideurId, @RequestParam String commentaire) {
        return ressourceService.rejeterRessource(id, valideurId, commentaire);
    }

    @PutMapping("/ressources/{id}/revision")
    public Ressource demanderRevision(@PathVariable Long id, @RequestParam Long valideurId, @RequestParam String commentaire) {
        return ressourceService.demanderRevision(id, valideurId, commentaire);
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
    }

    @PostMapping("/users")
    public User createUser(@RequestBody User user, @RequestParam Role role) {
        user.setRole(role);
        return userService.createUser(user);
    }

    @PutMapping("/users/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user) {
        return userService.updateUser(id, user);
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @PutMapping("/users/{id}/activer")
    public User activerUser(@PathVariable Long id) {
        return userService.activateUser(id);
    }

    @PutMapping("/users/{id}/desactiver")
    public User desactiverUser(@PathVariable Long id) {
        return userService.deactivateUser(id);
    }
}