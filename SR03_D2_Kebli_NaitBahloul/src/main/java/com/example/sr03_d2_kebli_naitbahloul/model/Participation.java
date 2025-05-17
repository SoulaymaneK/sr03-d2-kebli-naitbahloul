package com.example.sr03_d2_kebli_naitbahloul.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Participation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "utilisateur_id", nullable = false)
    private com.example.sr03_d2_kebli_naitbahloul.model.Utilisateur utilisateur;

    @ManyToOne
    @JoinColumn(name = "canal_id", nullable = false)
    private Canal canal;

    private LocalDateTime dateInvitation;

    private String role = "participant"; // Par défaut

    // Constructeurs

    public Participation() {}

    public Participation(com.example.sr03_d2_kebli_naitbahloul.model.Utilisateur utilisateur, Canal canal) {
        this.utilisateur = utilisateur;
        this.canal = canal;
        this.dateInvitation = LocalDateTime.now();
    }

    // Getters et Setters

    public Long getId() {
        return id;
    }

    public com.example.sr03_d2_kebli_naitbahloul.model.Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(com.example.sr03_d2_kebli_naitbahloul.model.Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Canal getCanal() {
        return canal;
    }

    public void setCanal(Canal canal) {
        this.canal = canal;
    }

    public LocalDateTime getDateInvitation() {
        return dateInvitation;
    }

    public void setDateInvitation(LocalDateTime dateInvitation) {
        this.dateInvitation = dateInvitation;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}