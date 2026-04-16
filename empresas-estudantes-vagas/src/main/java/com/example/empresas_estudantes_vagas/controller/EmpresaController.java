package com.example.empresas_estudantes_vagas.controller;

import com.example.empresas_estudantes_vagas.model.Empresa;
import com.example.empresas_estudantes_vagas.repository.EmpresaRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/empresas")
public class EmpresaController {
    
    @Autowired
    private EmpresaRepo empresaRepo;
    
    @GetMapping
    public Iterable<Empresa> getAll() {
        return empresaRepo.findAll();
    }
    
    @PostMapping
    public Empresa create(@RequestBody Empresa empresa) {
        return empresaRepo.save(empresa);
    }
}
