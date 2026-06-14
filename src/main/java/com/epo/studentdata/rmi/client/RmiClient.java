package com.epo.studentdata.rmi.client;

import com.epo.studentdata.model.Ressource;
import com.epo.studentdata.rmi.IRessourceRemote;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

public class RmiClient {

    public static void main(String[] args) {
        try {
            // Connexion au registry RMI
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);

            // Récupération du service distant
            IRessourceRemote ressourceService = (IRessourceRemote) registry.lookup("RessourceService");

            System.out.println("✅ Connecté au service RMI distant");

            // Test 1 : Récupérer les ressources de la filière "Genie informatique"
            System.out.println("\n=== Test 1 : Recherche par filière ===");
            List<Ressource> ressources = ressourceService.getRessourcesApprouveesByFiliere("Genie informatique");
            System.out.println("Ressources trouvées : " + ressources.size());

            // Test 2 : Récupérer toutes les filières
            System.out.println("\n=== Test 2 : Liste des filières ===");
            var filieres = ressourceService.getAllFilieres();
            filieres.forEach(f -> System.out.println("  - " + f.getNom()));

            System.out.println("\n✅ Appels RMI réussis !");

        } catch (Exception e) {
            System.err.println("❌ Erreur RMI : " + e.getMessage());
            e.printStackTrace();
        }
    }
}