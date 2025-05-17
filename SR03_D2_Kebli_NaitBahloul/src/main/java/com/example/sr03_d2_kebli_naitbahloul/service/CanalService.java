package com.example.sr03_d2_kebli_naitbahloul.service;

import com.example.sr03_d2_kebli_naitbahloul.model.Canal;
import com.example.sr03_d2_kebli_naitbahloul.model.Utilisateur;
import com.example.sr03_d2_kebli_naitbahloul.repository.CanalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CanalService {

    private final CanalRepository canalRepository;

    @Autowired
    public CanalService(CanalRepository canalRepository) {
        this.canalRepository = canalRepository;
    }

    // Créer un nouveau canal
    public boolean creerCanal(String titre, String description, LocalDateTime dateDebut, Integer duree, Utilisateur createur) {
        if (dateDebut.isBefore(LocalDateTime.now())) {
            return false; // On ne permet pas de planifier un canal dans le passé
        }
        Canal canal = new Canal(titre, description, dateDebut, duree, createur);
        canalRepository.save(canal);
        return true;
    }

    // Lister tous les canaux
    public List<Canal> listerTousLesCanaux() {
        return canalRepository.findAll();
    }

    // Lister les canaux créés par un utilisateur
    public List<Canal> listerCanauxParCreateur(Utilisateur utilisateur) {
        return canalRepository.findByCreateur(utilisateur);
    }

    // Récupérer un canal par ID
    public Optional<Canal> getCanalParId(Long id) {
        return canalRepository.findById(id);
    }

    // Désactiver un canal
    public boolean desactiverCanal(Long id) {
        Optional<Canal> optCanal = canalRepository.findById(id);
        if (optCanal.isPresent()) {
            Canal c = optCanal.get();
            c.setActif(false);
            canalRepository.save(c);
            return true;
        }
        return false;
    }

    public boolean activerCanal(Long id) {
        Optional<Canal> opt = canalRepository.findById(id);
        if (opt.isPresent()) {
            Canal canal = opt.get();
            canal.setActif(true);
            canalRepository.save(canal);
            return true;
        }
        return false;
    }

    public List<Canal> chercherParTitre(Utilisateur createur, String filtre) {
        return canalRepository.findByCreateurAndTitreContainingIgnoreCase(createur, filtre);
    }



}
