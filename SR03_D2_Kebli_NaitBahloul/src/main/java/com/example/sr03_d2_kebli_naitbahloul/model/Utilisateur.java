package com.example.sr03_d2_kebli_naitbahloul.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    private String prenom;

    @Column(unique = true, nullable = false)
    private String email;

    private String motDePasse;

    @Lob
    @Column(length = 1048576) // Limite à 1MB
    private byte[] photoProfil;

    private boolean admin;

    private boolean actif = true;

    // Relations

    @OneToMany(mappedBy = "createur", cascade = CascadeType.ALL)
    private List<Canal> canauxCrees;

    @OneToMany(mappedBy = "utilisateur", cascade = CascadeType.ALL)
    private List<Participation> participations;

    // Constructeurs

    public Utilisateur() {}

    // Getters et Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public byte[] getPhotoProfil() {
        return photoProfil;
    }

    public void setPhotoProfil(byte[] photoProfil) {
        this.photoProfil = photoProfil;
    }

    public boolean getAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {this.admin = admin;}

    public boolean isActif() {
        return actif;
    }

    public void setActif(boolean actif) {
        this.actif = actif;
    }

    public List<Canal> getCanauxCrees() {
        return canauxCrees;
    }

    public void setCanauxCrees(List<Canal> canauxCrees) {
        this.canauxCrees = canauxCrees;
    }

    public List<Participation> getParticipations() {
        return participations;
    }

    public void setParticipations(List<Participation> participations) {
        this.participations = participations;
    }
}