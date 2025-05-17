package com.example.sr03_d2_kebli_naitbahloul.controller;

import com.example.sr03_d2_kebli_naitbahloul.model.Canal;
import com.example.sr03_d2_kebli_naitbahloul.model.Utilisateur;
import com.example.sr03_d2_kebli_naitbahloul.service.CanalService;
import com.example.sr03_d2_kebli_naitbahloul.service.ParticipationService;
import com.example.sr03_d2_kebli_naitbahloul.service.UtilisateurService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/canaux")
public class CanalController {

    private final CanalService canalService;

    @Autowired
    public CanalController(CanalService canalService) {
        this.canalService = canalService;
    }

    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private ParticipationService participationService;

    // Affiche le formulaire de création + liste des canaux
    @GetMapping
    public String afficherCanaux(@RequestParam(value = "filtre", required = false) String filtre,
                                 Model model,
                                 HttpSession session) {
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateurConnecte");
        if (utilisateur == null) {
            return "redirect:/home/login";
        }

        List<Canal> canaux;
        if (filtre != null && !filtre.trim().isEmpty()) {
            canaux = canalService.chercherParTitre(utilisateur, filtre.trim());
            model.addAttribute("filtre", filtre);
        } else {
            canaux = canalService.listerCanauxParCreateur(utilisateur);
        }

        model.addAttribute("canaux", canaux);
        model.addAttribute("utilisateurs", utilisateurService.listerUtilisateursActifs());

        Map<Long, List<Utilisateur>> participantsParCanal = new HashMap<>();
        for (Canal c : canaux) {
            participantsParCanal.put(c.getId(), participationService.getParticipantsParCanal(c));
        }

        model.addAttribute("participantsParCanal", participantsParCanal);
        return "canaux";
    }



    // Créer un canal
    @PostMapping("/creer")
    public String creerCanal(@RequestParam String titre,
                             @RequestParam String description,
                             @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateDebut,
                             @RequestParam Integer duree,
                             HttpSession session,
                             Model model) {
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateurConnecte");
        if (utilisateur == null) {
            return "redirect:/home/login";
        }

        boolean success = canalService.creerCanal(titre, description, dateDebut, duree, utilisateur);
        if (!success) {
            model.addAttribute("message", "Erreur : la date de début doit être dans le futur.");
        } else {
            model.addAttribute("message", "Canal créé avec succès.");
        }

        model.addAttribute("canaux", canalService.listerCanauxParCreateur(utilisateur));
        return "redirect:/canaux";
    }

    // Désactiver un canal
    @PostMapping("/desactiver/{id}")
    public String desactiverCanal(@PathVariable Long id, HttpSession session, Model model) {
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateurConnecte");
        if (utilisateur == null) {
            return "redirect:/home/login";
        }

        canalService.desactiverCanal(id);
        model.addAttribute("message", "Canal désactivé.");
        model.addAttribute("canaux", canalService.listerCanauxParCreateur(utilisateur));
        return "redirect:/canaux";
    }

    @PostMapping("/activer/{id}")
    public String activerCanal(@PathVariable Long id, HttpSession session) {
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateurConnecte");
        if (utilisateur == null) {
            return "redirect:/home/login";
        }

        canalService.activerCanal(id);
        return "redirect:/canaux";
    }


    @PostMapping("/inviter")
    public String inviterUtilisateur(@RequestParam Long canalId,
                                     @RequestParam Long userId,
                                     HttpSession session,
                                     Model model) {

        Utilisateur admin = (Utilisateur) session.getAttribute("utilisateurConnecte");
        if (admin == null || !admin.getAdmin()) {
            return "redirect:/home/login";
        }

        Canal canal = canalService.getCanalParId(canalId).orElseThrow();
        Utilisateur invite = utilisateurService.getUtilisateurParId(userId);

        boolean success = participationService.inviterUtilisateur(canal, invite);

        model.addAttribute("message", success
                ? "Utilisateur invité avec succès."
                : "Cet utilisateur est déjà membre de ce canal.");

        model.addAttribute("canaux", canalService.listerCanauxParCreateur(admin));
        model.addAttribute("utilisateurs", utilisateurService.listerUtilisateursActifs());

        return "redirect:/canaux";
    }
}
