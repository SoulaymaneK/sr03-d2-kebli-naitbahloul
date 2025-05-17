package com.example.sr03_d2_kebli_naitbahloul.repository;

import com.example.sr03_d2_kebli_naitbahloul.model.Canal;
import com.example.sr03_d2_kebli_naitbahloul.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CanalRepository extends JpaRepository<Canal, Long> {

    List<Canal> findByCreateur(Utilisateur createur);

    List<Canal> findByActif(boolean actif);

    List<Canal> findByTitreContainingIgnoreCase(String titre);

    List<Canal> findByCreateurAndTitreContainingIgnoreCase(Utilisateur createur, String titre);
}

