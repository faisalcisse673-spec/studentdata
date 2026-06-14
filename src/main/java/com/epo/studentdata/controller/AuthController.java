package com.epo.studentdata.controller;

import com.epo.studentdata.model.User;
import com.epo.studentdata.service.IUserService;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final IUserService userService;

    public AuthController(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestParam String email, @RequestParam String password) {
        boolean authenticated = userService.authenticate(email, password);
        Map<String, Object> response = new HashMap<>();

        if (authenticated) {
            Optional<User> user = userService.getUserByEmail(email);
            response.put("success", true);
            response.put("message", "Connexion réussie");
            response.put("user", user.get());
        } else {
            response.put("success", false);
            response.put("message", "Email ou mot de passe incorrect");
        }
        return response;
    }

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return userService.createUser(user);
    }
}