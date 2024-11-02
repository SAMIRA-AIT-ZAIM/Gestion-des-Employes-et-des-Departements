package com.example.demo.controller;

import com.example.demo.entities.Employe;
import com.example.demo.entities.Departement;
import com.example.demo.repository.EmployeRepository;
import com.example.demo.repository.DepartementRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/employes") // Ajout de RequestMapping pour grouper les routes
public class EmployeController {

    @Autowired
    private EmployeRepository employeRepository;

    @Autowired
    private DepartementRepository departementRepository;
    // Page des options pour la gestion des employés
    @GetMapping("/options")
    public String gestionEmployeOptions() {
        return "employe"; // Nom du template Thymeleaf pour la page des options des employés
    }

    // Afficher la liste des employés
    @GetMapping
    public String listeEmployes(Model model) {
        model.addAttribute("employes", employeRepository.findAll());
        return "liste-employes";
    }

    // Afficher le formulaire de création d'employé
    @GetMapping("/add")
    public String showAddEmployeForm(Model model) {
        model.addAttribute("employe", new Employe());
        List<Departement> departements = departementRepository.findAll();
        model.addAttribute("departements", departements);
        return "add-employe";
    }

    // Traiter la soumission du formulaire d'ajout d'un employé
    @PostMapping("/add")
    public String addEmploye(@Valid @ModelAttribute("employe") Employe employe,
                             BindingResult result, Model model) {
        // Vérifiez les erreurs de validation
        if (result.hasErrors()) {
            model.addAttribute("departements", departementRepository.findAll());
            return "add-employe";
        }

        Long departementId = employe.getDepartement() != null ? employe.getDepartement().getId() : null;

        if (departementId == null || !departementRepository.existsById(departementId)) {
            result.rejectValue("departement", "error.employe", "Le département doit exister");
            model.addAttribute("departements", departementRepository.findAll());
            return "add-employe";
        }

        Departement departement = departementRepository.findById(departementId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid department ID"));
        employe.setDepartement(departement); // Assurez-vous que cela fonctionne correctement

        employeRepository.save(employe);
        return "redirect:/employes"; // Redirection vers la liste des employés
    }


    @GetMapping("/edit/{id}")
    public String showUpdateEmployeForm(@PathVariable("id") Long id, Model model) {
        Employe employe = employeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid employe Id:" + id));
        model.addAttribute("employe", employe);
        // Récupérer la liste des départements pour le sélecteur
        List<Departement> departements = departementRepository.findAll();
        model.addAttribute("departements", departements);
        return "update-employe"; // Nom du fichier HTML
    }



    // Méthode pour traiter la mise à jour
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        Employe employe = employeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid employee Id:" + id));
        model.addAttribute("employe", employe);
        return "update-employe";
    }

    @PostMapping("/update/{id}")
    public String updateEmploye(@PathVariable("id") Long id, @Valid Employe employe, BindingResult result, Model model) {
        if (result.hasErrors()) {
            employe.setId(id);
            return "update-employe";
        }
        employeRepository.save(employe);
        return "redirect:/employes";
    }


    // Supprimer un employé
    @GetMapping("/delete/{id}")
    public String deleteEmploye(@PathVariable("id") Long id) {
        System.out.println("Tentative de suppression de l'employé avec ID: " + id);

        Employe employe = employeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid employe Id:" + id));

        employeRepository.delete(employe);
        return "redirect:/employes"; // Redirection vers la liste des employés
    }

    // Exemple de méthode pour tester l'insertion d'un employé avec un département
    @GetMapping("/test-add-employe")
    public String testAddEmploye() {
        // Supposons qu'il existe un département avec l'ID 1 dans la base de données
        Departement departement = departementRepository.findById(1L)
                .orElseThrow(() -> new IllegalArgumentException("Invalid department ID"));

        // Création d'un nouvel employé avec ce département
        Employe employe = new Employe();
        employe.setNom("NomTest");
        employe.setPoste("PosteTest");
        employe.setSalaire(50000);
        employe.setDepartement(departement); // Associe l'employé au département

        // Sauvegarde de l'employé
        employeRepository.save(employe);

        // Retourne un message ou redirige vers une autre page après le test
        return "redirect:/employes"; // Redirige vers la liste des employés, par exemple
    }
}
