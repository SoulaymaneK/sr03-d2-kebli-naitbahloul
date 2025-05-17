package com.example.sr03_d2_kebli_naitbahloul.repository;

import com.example.sr03_d2_kebli_naitbahloul.model.Participation;
import com.example.sr03_d2_kebli_naitbahloul.model.Utilisateur;
import com.example.sr03_d2_kebli_naitbahloul.model.Canal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParticipationRepository extends JpaRepository<Participation, Long> {

    List<Participation> findByUtilisateur(Utilisateur utilisateur);

    List<Participation> findByCanal(Canal canal);

    Optional<Participation> findByUtilisateurAndCanal(Utilisateur utilisateur, Canal canal);

    boolean existsByUtilisateurAndCanal(Utilisateur utilisateur, Canal canal);
}
