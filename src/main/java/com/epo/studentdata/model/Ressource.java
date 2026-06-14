package com.epo.studentdata.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ressources")
public class Ressource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titre;
    private String description;
    private String fileName;  // ← NOUVEAU : nom du fichier stocké
    private String originalFileName;  // ← NOUVEAU : nom original
    private LocalDateTime dateUpload;

    @Enumerated(EnumType.STRING)
    private TypeRessource type;

    @ManyToOne
    @JoinColumn(name = "matiere_id")
    @JsonIgnoreProperties("ressources")
    private Matiere matiere;

    private String anneeAcademique;
    private Integer semestre;

    @ManyToOne
    @JoinColumn(name = "filiere_id")
    @JsonIgnoreProperties("ressources")
    private Filiere filiere;

    @Enumerated(EnumType.STRING)
    private StatutRessource statut = StatutRessource.EN_ATTENTE;

    @ManyToOne
    @JoinColumn(name = "auteur_id")
    @JsonIgnoreProperties("ressourcesSoumises")
    private User auteur;

    @ManyToOne
    @JoinColumn(name = "valideur_id")
    @JsonIgnoreProperties({"ressourcesSoumises", "password"})
    private User valideur;

    private LocalDateTime datePublication;

    private String commentaireRejet;

    public Ressource() {}

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitre() { return titre; }
    public void setTitre(String titre) { this.titre = titre; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }

    public String getOriginalFileName() { return originalFileName; }
    public void setOriginalFileName(String originalFileName) { this.originalFileName = originalFileName; }

    public LocalDateTime getDateUpload() { return dateUpload; }
    public void setDateUpload(LocalDateTime dateUpload) { this.dateUpload = dateUpload; }

    public TypeRessource getType() { return type; }
    public void setType(TypeRessource type) { this.type = type; }

    public Matiere getMatiere() { return matiere; }
    public void setMatiere(Matiere matiere) { this.matiere = matiere; }

    public String getAnneeAcademique() { return anneeAcademique; }
    public void setAnneeAcademique(String anneeAcademique) { this.anneeAcademique = anneeAcademique; }

    public Integer getSemestre() { return semestre; }
    public void setSemestre(Integer semestre) { this.semestre = semestre; }

    public Filiere getFiliere() { return filiere; }
    public void setFiliere(Filiere filiere) { this.filiere = filiere; }

    public StatutRessource getStatut() { return statut; }
    public void setStatut(StatutRessource statut) { this.statut = statut; }

    public User getAuteur() { return auteur; }
    public void setAuteur(User auteur) { this.auteur = auteur; }

    public User getValideur() { return valideur; }
    public void setValideur(User valideur) { this.valideur = valideur; }

    public LocalDateTime getDatePublication() { return datePublication; }
    public void setDatePublication(LocalDateTime datePublication) { this.datePublication = datePublication; }

    public String getCommentaireRejet() { return commentaireRejet; }
    public void setCommentaireRejet(String commentaireRejet) { this.commentaireRejet = commentaireRejet; }
}