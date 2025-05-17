package com.example.sr03_d2_kebli_naitbahloul.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Canal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titre;

    private String description;

    private LocalDateTime dateDebut;

    private Integer duree; // en minutes

    private boolean actif = true;

    // Créateur du canal
    @ManyToOne
    @JoinColumn(name = "createur_id", nullable = false)
    private Utilisateur createur;

    // Participants via entité de jointure
    @OneToMany(mappedBy = "canal", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Participation> participations;

    // Constructeurs

    public Canal() {}

    public Canal(String titre, String description, LocalDateTime dateDebut, Integer duree, Utilisateur createur) {
        this.titre = titre;
        this.description = description;
        this.dateDebut = dateDebut;
        this.duree = duree;
        this.createur = createur;
        this.actif = true;
    }

    // Getters et Setters

    public Long getId() {
        return id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDateTime dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Integer getDuree() {
        return duree;
    }

    public void setDuree(Integer duree) {
        this.duree = duree;
    }

    public boolean isActif() {
        return actif;
    }

    public void setActif(boolean actif) {
        this.actif = actif;
    }

    public Utilisateur getCreateur() {
        return createur;
    }

    public void setCreateur(Utilisateur createur) {
        this.createur = createur;
    }

    public List<Participation> getParticipations() {
        return participations;
    }

    public void setParticipations(List<Participation> participations) {
        this.participations = participations;
    }
}