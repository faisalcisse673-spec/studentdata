package com.epo.studentdata.config;

import com.epo.studentdata.model.Role;
import com.epo.studentdata.model.User;
import com.epo.studentdata.service.IUserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final IUserService userService;

    public DataInitializer(IUserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
        if (userService.getAllUsers().isEmpty()) {
            User admin = new User("admin@test.com", "admin", "Admin", "Admin", Role.ADMIN);
            User prof = new User("prof@test.com", "prof", "Prof", "Prof", Role.ENSEIGNANT);
            User etudiant = new User("etudiant@test.com", "etudiant", "Etudiant", "Etudiant", Role.ETUDIANT);
            
            userService.createUser(admin);
            userService.createUser(prof);
            userService.createUser(etudiant);
            
            System.out.println("=========================================================");
            System.out.println("Comptes de test créés avec succès !");
            System.out.println("Admin: admin@test.com / admin");
            System.out.println("Prof: prof@test.com / prof");
            System.out.println("Etudiant: etudiant@test.com / etudiant");
            System.out.println("=========================================================");
        } else {
            System.out.println("Les comptes existent déjà dans la base de données.");
        }
    }
}
