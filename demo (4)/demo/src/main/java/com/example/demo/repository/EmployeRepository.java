package com.example.demo.repository;

import com.example.demo.entities.Departement;
import com.example.demo.entities.Employe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeRepository extends JpaRepository<Employe, Long> {
    List<Employe> findByDepartement(Departement departement); // Méthode nécessaire
}
