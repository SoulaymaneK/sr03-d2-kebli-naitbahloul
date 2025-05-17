package com.example.sr03_d2_kebli_naitbahloul.config;

import com.example.sr03_d2_kebli_naitbahloul.model.Utilisateur;
import com.example.sr03_d2_kebli_naitbahloul.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class DefaultAdminInitializer {

    private final UtilisateurRepository utilisateurRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DefaultAdminInitializer(UtilisateurRepository utilisateurRepository, PasswordEncoder passwordEncoder) {
        this.utilisateurRepository = utilisateurRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void initAdminUser() {
        boolean adminExiste = utilisateurRepository.findByAdmin(true).stream().findFirst().isPresent();

        if (!adminExiste) {
            Utilisateur admin = new Utilisateur();
            admin.setPrenom("Admin");
            admin.setNom("ParDéfaut");
            admin.setEmail("admin@mail.com");
            admin.setMotDePasse(passwordEncoder.encode("admin123"));
            admin.setAdmin(true);
            admin.setActif(true);

            utilisateurRepository.save(admin);

            System.out.println("[INFO] Admin par défaut créé : admin@mail.com / admin123");
        }
    }
}
