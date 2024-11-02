package com.example.demo.service;

import com.example.demo.entities.Departement;
import com.example.demo.entities.Employe;
import com.example.demo.repository.EmployeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeService {
    @Autowired
    private EmployeRepository employeRepository;

    public List<Employe> getEmployesByDepartement(Departement departement) {
        return employeRepository.findByDepartement(departement);
    }
}
