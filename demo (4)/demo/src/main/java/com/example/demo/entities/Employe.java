package com.example.demo.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "employe")
public class Employe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le nom est obligatoire")
    private String nom;

    @NotBlank(message = "Le poste est obligatoire")
    private String poste;

    private Double salaire;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "departement_id")
    private Departement departement;

    // Constructeurs
    public Employe() {}

    public Employe(Departement departement, String nom, String poste, double salaire) {
        this.departement = departement;
        this.nom = nom;
        this.poste = poste;
        this.salaire = salaire;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPoste() {
        return poste;
    }

    public void setPoste(String poste) {
        this.poste = poste;
    }

    public Double getSalaire() {
        return salaire != null ? salaire : 0.0;
    }

    public void setSalaire(double salaire) {
        this.salaire = salaire;
    }

    public Departement getDepartement() {
        return departement;
    }

    public void setDepartement(Departement departement) {
        this.departement = departement;
    }

    public void setId(Long id) {
        this.id = id;
    }
    @Override
    public String toString() {
        return this.nom; // ou un autre attribut qui représente le département
    }

}
