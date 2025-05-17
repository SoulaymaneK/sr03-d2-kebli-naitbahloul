package com.example.sr03_d2_kebli_naitbahloul.repository;

import com.example.sr03_d2_kebli_naitbahloul.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {

    Optional<Utilisateur> findByEmail(String email);

    List<Utilisateur> findByAdmin(boolean admin);

    List<Utilisateur> findByActif(boolean actif);

    boolean existsByEmail(String email);
}
