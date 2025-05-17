package com.example.sr03_d2_kebli_naitbahloul.controller;

import com.example.sr03_d2_kebli_naitbahloul.model.Utilisateur;
import com.example.sr03_d2_kebli_naitbahloul.model.Canal;
import com.example.sr03_d2_kebli_naitbahloul.service.CanalService;
import com.example.sr03_d2_kebli_naitbahloul.service.UtilisateurService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.Optional;
import java.util.List;

@Controller
@RequestMapping("/home")
public class UtilisateurController {

    private final UtilisateurService utilisateurService;

    @Autowired
    public UtilisateurController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    @Autowired
    private CanalService canalService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public String afficherUtilisateurs(Model model, HttpSession session) {
        Utilisateur u = (Utilisateur) session.getAttribute("utilisateurConnecte");
        if (u == null || !u.getAdmin()) {
            return "redirect:/home/login";
        }
        model.addAttribute("users", utilisateurService.listerUtilisateurs());
        return "home";
    }

    @GetMapping("/login")
    public String afficherLoginForm() {
        return "login";
    }

    @GetMapping("/utilisateur/{id}/canaux")
    public String afficherCanauxUtilisateur(@PathVariable Long id,
                                            @RequestParam(required = false) String filtre,
                                            Model model,
                                            HttpSession session) {
        Utilisateur admin = (Utilisateur) session.getAttribute("utilisateurConnecte");
        if (admin == null || !admin.getAdmin()) {
            return "redirect:/home/login";
        }

        Utilisateur utilisateur = utilisateurService.getUtilisateurParId(id);

        List<Canal> canaux = (filtre != null && !filtre.trim().isEmpty())
                ? canalService.chercherParTitre(utilisateur, filtre.trim())
                : canalService.listerCanauxParCreateur(utilisateur);

        model.addAttribute("utilisateur", utilisateur);
        model.addAttribute("canaux", canaux);
        model.addAttribute("filtre", filtre);
        return "canaux-utilisateur";
    }


    @PostMapping("/login")
    public String traiterLogin(@RequestParam String email,
                               @RequestParam String motDePasse,
                               Model model,
                               HttpSession session) {
        Optional<Utilisateur> optUser = utilisateurService.getUtilisateurParEmail(email);

        if (optUser.isPresent()) {
            Utilisateur user = optUser.get();

            // Comparaison en clair (pas de hash)
            if (passwordEncoder.matches(motDePasse, user.getMotDePasse())) {
                session.setAttribute("utilisateurConnecte", user);

                if (user.getAdmin()) {
                    return "redirect:/home";
                } else {
                    return "redirect:/utilisateur"; // à implémenter plus tard
                }
            }
        }

        model.addAttribute("error", "Email ou mot de passe incorrect.");
        return "login";
    }

    @GetMapping("/logout")
    public String deconnexion(HttpSession session) {
        session.invalidate();
        return "redirect:/home/login";
    }

    @PostMapping("/users")
    public String ajouterUtilisateur(@ModelAttribute Utilisateur utilisateur, Model model) {
        boolean succes = utilisateurService.ajouterUtilisateur(utilisateur);
        if (succes) {
            model.addAttribute("message", "Utilisateur ajouté avec succès !");
        } else {
            model.addAttribute("message", "Email déjà utilisé !");
        }
        model.addAttribute("users", utilisateurService.listerUtilisateurs());
        return "home";
    }

    @PostMapping("/delete/{id}")
    public String supprimerUtilisateur(@PathVariable Long id, Model model) {
        utilisateurService.supprimerUtilisateur(id);
        model.addAttribute("message", "Utilisateur supprimé.");
        model.addAttribute("users", utilisateurService.listerUtilisateurs());
        return "home";
    }

    @PostMapping("/desactiver/{id}")
    public String desactiverUtilisateur(@PathVariable Long id, Model model) {
        utilisateurService.desactiverUtilisateur(id);
        model.addAttribute("message", "Utilisateur désactivé.");
        model.addAttribute("users", utilisateurService.listerUtilisateurs());
        return "home";
    }

    @PostMapping("/activer/{id}")
    public String activerUtilisateur(@PathVariable Long id, Model model) {
        utilisateurService.activerUtilisateur(id);
        model.addAttribute("message", "Utilisateur réactivé.");
        model.addAttribute("users", utilisateurService.listerUtilisateurs());
        return "home";
    }

    @GetMapping("/modifier/{id}")
    public String afficherFormulaireModification(@PathVariable Long id, Model model) {
        Utilisateur utilisateur = utilisateurService.getUtilisateurParId(id);
        model.addAttribute("user", utilisateur);
        return "modifier-utilisateur";
    }

    @PostMapping("/modifier")
    public String modifierUtilisateur(@ModelAttribute Utilisateur utilisateur, Model model) {
        utilisateurService.modifierUtilisateur(utilisateur);
        model.addAttribute("message", "Utilisateur modifié avec succès.");
        model.addAttribute("users", utilisateurService.listerUtilisateurs());
        return "home";
    }
}