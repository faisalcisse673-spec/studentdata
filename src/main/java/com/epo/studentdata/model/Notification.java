package com.epo.studentdata.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "utilisateur_id", nullable = false)
    @JsonIgnoreProperties({"password", "ressourcesSoumises"})
    private User utilisateur;

    @Column(nullable = false, length = 500)
    private String message;

    private String type; // ex: SOUUMISSION_REJETEE, SOUUMISSION_APPROUVEE
    
    private Boolean lu = false;
    private LocalDateTime dateCreation;

    public Notification() {}

    public Notification(User utilisateur, String message, String type) {
        this.utilisateur = utilisateur;
        this.message = message;
        this.type = type;
        this.lu = false;
        this.dateCreation = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUtilisateur() { return utilisateur; }
    public void setUtilisateur(User utilisateur) { this.utilisateur = utilisateur; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public Boolean getLu() { return lu; }
    public void setLu(Boolean lu) { this.lu = lu; }

    public LocalDateTime getDateCreation() { return dateCreation; }
    public void setDateCreation(LocalDateTime dateCreation) { this.dateCreation = dateCreation; }
}
