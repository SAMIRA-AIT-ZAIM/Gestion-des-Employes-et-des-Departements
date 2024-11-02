package com.example.demo.service;

import com.example.demo.entities.Departement;
import com.example.demo.repository.DepartementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DepartementService {

    @Autowired
    private DepartementRepository departementRepository;

    public Optional<Departement> findById(Long id) {
        return departementRepository.findById(id);
    }
}
