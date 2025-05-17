package com.example.sr03_d2_kebli_naitbahloul.service;

import com.example.sr03_d2_kebli_naitbahloul.model.Utilisateur;
import com.example.sr03_d2_kebli_naitbahloul.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UtilisateurService {

    private final UtilisateurRepository utilisateurRepository;

    @Autowired
    public UtilisateurService(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean ajouterUtilisateur(Utilisateur utilisateur) {
        if (utilisateurRepository.existsByEmail(utilisateur.getEmail())) {
            return false;
        }

        utilisateur.setMotDePasse(passwordEncoder.encode(utilisateur.getMotDePasse()));
        utilisateurRepository.save(utilisateur);
        return true;
    }

    public void supprimerUtilisateur(Long id) {
        utilisateurRepository.deleteById(id);
    }

    public boolean desactiverUtilisateur(Long id) {
        Optional<Utilisateur> optUser = utilisateurRepository.findById(id);
        if (optUser.isPresent()) {
            Utilisateur u = optUser.get();
            u.setActif(false);
            utilisateurRepository.save(u);
            return true;
        }
        return false;
    }

    public boolean activerUtilisateur(Long id) {
        Optional<Utilisateur> optUser = utilisateurRepository.findById(id);
        if (optUser.isPresent()) {
            Utilisateur u = optUser.get();
            u.setActif(true);
            utilisateurRepository.save(u);
            return true;
        }
        return false;
    }

    public List<Utilisateur> listerUtilisateurs() {
        return utilisateurRepository.findAll();
    }

    public List<Utilisateur> listerAdmins() {
        return utilisateurRepository.findByAdmin(true);
    }

    public List<Utilisateur> listerUtilisateursActifs() {
        return utilisateurRepository.findByActif(true);
    }

    public Utilisateur getUtilisateurParId(Long id) {
        return utilisateurRepository.findById(id).orElseThrow();
    }

    public Optional<Utilisateur> getUtilisateurParEmail(String email) {
        return utilisateurRepository.findByEmail(email);
    }

    public void modifierUtilisateur(Utilisateur utilisateurModifie) {
        Utilisateur utilisateur = utilisateurRepository.findById(utilisateurModifie.getId()).orElseThrow();
        utilisateur.setNom(utilisateurModifie.getNom());
        utilisateur.setPrenom(utilisateurModifie.getPrenom());
        utilisateur.setEmail(utilisateurModifie.getEmail());
        utilisateur.setAdmin(utilisateurModifie.getAdmin());
        utilisateurRepository.save(utilisateur);
    }
}