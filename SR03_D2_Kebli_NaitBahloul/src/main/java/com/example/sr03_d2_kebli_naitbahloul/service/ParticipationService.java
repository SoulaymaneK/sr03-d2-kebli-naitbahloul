package com.example.sr03_d2_kebli_naitbahloul.service;

import com.example.sr03_d2_kebli_naitbahloul.model.Canal;
import com.example.sr03_d2_kebli_naitbahloul.model.Participation;
import com.example.sr03_d2_kebli_naitbahloul.model.Utilisateur;
import com.example.sr03_d2_kebli_naitbahloul.repository.ParticipationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ParticipationService {

    private final ParticipationRepository participationRepository;

    @Autowired
    public ParticipationService(ParticipationRepository participationRepository) {
        this.participationRepository = participationRepository;
    }

    public boolean inviterUtilisateur(Canal canal, Utilisateur utilisateur) {
        if (participationRepository.existsByUtilisateurAndCanal(utilisateur, canal)) {
            return false; // déjà invité
        }

        Participation participation = new Participation();
        participation.setCanal(canal);
        participation.setUtilisateur(utilisateur);
        participation.setDateInvitation(LocalDateTime.now());
        participation.setRole("participant");

        participationRepository.save(participation);
        return true;
    }

    public List<Utilisateur> getParticipantsParCanal(Canal canal) {
        return participationRepository.findByCanal(canal)
                .stream()
                .map(Participation::getUtilisateur)
                .toList();
    }
}
