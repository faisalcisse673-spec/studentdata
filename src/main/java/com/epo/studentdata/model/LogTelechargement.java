package com.epo.studentdata.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "logs_telechargement")
public class LogTelechargement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "utilisateur_id")
    @JsonIgnoreProperties({"password", "ressourcesSoumises"})
    private User utilisateur;

    @ManyToOne
    @JoinColumn(name = "document_id")
    @JsonIgnoreProperties({"auteur", "valideur", "filiere", "matiere"})
    private Ressource document;

    private LocalDateTime dateTelechargement;

    public LogTelechargement() {}

    public LogTelechargement(User utilisateur, Ressource document) {
        this.utilisateur = utilisateur;
        this.document = document;
        this.dateTelechargement = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUtilisateur() { return utilisateur; }
    public void setUtilisateur(User utilisateur) { this.utilisateur = utilisateur; }

    public Ressource getDocument() { return document; }
    public void setDocument(Ressource document) { this.document = document; }

    public LocalDateTime getDateTelechargement() { return dateTelechargement; }
    public void setDateTelechargement(LocalDateTime dateTelechargement) { this.dateTelechargement = dateTelechargement; }
}
