package com.epo.studentdata.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private String nom;
    private String prenom;

    @Column(unique = true)
    private String matricule;

    private String filiere;
    private String anneeEtudes;

    @Enumerated(EnumType.STRING)
    private Role role;

    private Boolean actif = true;
    private LocalDateTime dateInscription;

    @JsonIgnore
    @OneToMany(mappedBy = "auteur")
    private List<Ressource> ressourcesSoumises = new ArrayList<>();

    public User() {}

    public User(String email, String password, String nom, String prenom, Role role) {
        this.email = email;
        this.password = password;
        this.nom = nom;
        this.prenom = prenom;
        this.role = role;
        this.dateInscription = LocalDateTime.now();
        this.actif = true;
    }

    public User(String email, String password, String nom, String prenom, String matricule, String filiere, String anneeEtudes, Role role) {
        this.email = email;
        this.password = password;
        this.nom = nom;
        this.prenom = prenom;
        this.matricule = matricule;
        this.filiere = filiere;
        this.anneeEtudes = anneeEtudes;
        this.role = role;
        this.dateInscription = LocalDateTime.now();
        this.actif = true;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    public String getMatricule() { return matricule; }
    public void setMatricule(String matricule) { this.matricule = matricule; }

    public String getFiliere() { return filiere; }
    public void setFiliere(String filiere) { this.filiere = filiere; }

    public String getAnneeEtudes() { return anneeEtudes; }
    public void setAnneeEtudes(String anneeEtudes) { this.anneeEtudes = anneeEtudes; }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }

    public Boolean getActif() { return actif; }
    public void setActif(Boolean actif) { this.actif = actif; }

    public LocalDateTime getDateInscription() { return dateInscription; }
    public void setDateInscription(LocalDateTime dateInscription) { this.dateInscription = dateInscription; }

    public List<Ressource> getRessourcesSoumises() { return ressourcesSoumises; }
    public void setRessourcesSoumises(List<Ressource> ressourcesSoumises) { this.ressourcesSoumises = ressourcesSoumises; }
}