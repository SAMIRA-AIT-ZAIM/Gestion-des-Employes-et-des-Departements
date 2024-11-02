package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index() {
        return "index"; // Retourne la vue index.html
    }
    // Méthode pour afficher la page de gestion des départements
    @GetMapping("/departement")
    public String departement() {
        return "departement"; // Renvoie le nom de la page de gestion des départements
    }
}
