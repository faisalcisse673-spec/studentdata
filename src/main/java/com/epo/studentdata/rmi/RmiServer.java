package com.epo.studentdata.rmi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

@Component
public class RmiServer implements CommandLineRunner {

    @Autowired
    private RessourceRemoteImpl ressourceRemote;

    @Override
    public void run(String... args) throws Exception {
        try {
            // Création du registry RMI sur le port 1099
            Registry registry = LocateRegistry.createRegistry(1099);

            // Publication du service distant
            registry.rebind("RessourceService", ressourceRemote);

            System.out.println("========================================");
            System.out.println("✅ Serveur RMI démarré avec succès !");
            System.out.println("   Port : 1099");
            System.out.println("   Service : RessourceService");
            System.out.println("   URL : rmi://localhost:1099/RessourceService");
            System.out.println("========================================");
        } catch (Exception e) {
            System.err.println("❌ Erreur au démarrage du serveur RMI : " + e.getMessage());
            e.printStackTrace();
        }
    }
}