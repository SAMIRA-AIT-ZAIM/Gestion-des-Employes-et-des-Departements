package com.example.demo.controller;

import com.example.demo.entities.Departement;
import com.example.demo.repository.DepartementRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class DepartementController {

    @Autowired
    private DepartementRepository departementRepository;

    // Afficher la liste des départements
    @GetMapping("/departements")
    public String listeDepartements(Model model) {
        model.addAttribute("departements", departementRepository.findAll());
        return "liste-departements";
    }

    // Afficher le formulaire de création de département
    @GetMapping("/departements/add")
    public String showAddDepartementForm(Model model) {
        model.addAttribute("departement", new Departement());
        return "add-departement";
    }

    // Ajouter un département
    @PostMapping("/departements/add")
    public String addDepartement(@Valid Departement departement, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-departement";
        }
        departementRepository.save(departement);
        return "redirect:/departements";
    }

    // Afficher le formulaire de mise à jour du département
    @GetMapping("/departements/edit/{id}")
    public String showUpdateDepartementForm(@PathVariable("id") Long id, Model model) {
        Departement departement = departementRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid departement Id:" + id));
        model.addAttribute("departement", departement);
        return "update-departement";
    }

    // Mettre à jour le département
    @PostMapping("/departements/update/{id}")
    public String updateDepartement(@PathVariable("id") Long id, @Valid Departement departement, BindingResult result, Model model) {
        if (result.hasErrors()) {
            departement.setId(id);
            return "update-departement";
        }
        departementRepository.save(departement);
        return "redirect:/departements";
    }

    // Supprimer un département
    @GetMapping("/departements/delete/{id}")
    public String deleteDepartement(@PathVariable("id") Long id, Model model) {
        Departement departement = departementRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid departement Id:" + id));
        departementRepository.delete(departement);
        return "redirect:/departements";
    }


    // Afficher le détail d'un département avec ses employés
    @GetMapping("/departements/{id}")
    public String detailDepartement(@PathVariable("id") Long id, Model model) {
        // Récupérer le département par ID, ou lancer une exception si non trouvé
        Departement departement = departementRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid departement Id:" + id));

        // Ajouter le département au modèle
        model.addAttribute("departement", departement);

        // Ajouter les employés associés au département au modèle
        model.addAttribute("employes", departement.getEmployes());

        // Retourner le nom du template Thymeleaf à utiliser
        return "detail-departement";
    }
    @GetMapping("/departements/nom/{nom}")
    public String detailDepartementParNom(@PathVariable("nom") String nom, Model model) {
        // Récupérer le département par nom, ou lancer une exception si non trouvé
        Departement departement = departementRepository.findByNom(nom)
                .orElseThrow(() -> new IllegalArgumentException("Invalid departement name:" + nom));

        // Ajouter le département au modèle
        model.addAttribute("departement", departement);

        // Ajouter les employés associés au département au modèle
        model.addAttribute("employes", departement.getEmployes());

        // Retourner le nom du template Thymeleaf à utiliser
        return "detail-departement";
    }



}
