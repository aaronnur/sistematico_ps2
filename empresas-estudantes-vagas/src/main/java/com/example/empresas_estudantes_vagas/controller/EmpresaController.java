package com.example.empresasestudantesvagas.controller;

import com.example.empresasestudantesvagas.model.Empresa;
import com.example.empresasestudantesvagas.repository.EmpresaRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/empresas")
public class EmpresaController {
    
    @Autowired
    private EmpresaRepo empresaRepo;
    
    @GetMapping
    public List<Empresa> getAllEmpresas() {
        return empresaRepo.findAll();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Empresa> getEmpresaById(@PathVariable Long id) {
        Optional<Empresa> empresa = empresaRepo.findById(id);
        return empresa.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @GetMapping("/cnpj/{cnpj}")
    public ResponseEntity<Empresa> getEmpresaByCnpj(@PathVariable String cnpj) {
        Optional<Empresa> empresa = empresaRepo.findByCnpj(cnpj);
        return empresa.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<Empresa> createEmpresa(@RequestBody Empresa empresa) {
        if (empresaRepo.findByCnpj(empresa.getCnpj()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        Empresa savedEmpresa = empresaRepo.save(empresa);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEmpresa);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Empresa> updateEmpresa(@PathVariable Long id, @RequestBody Empresa empresaDetails) {
        Optional<Empresa> empresaOptional = empresaRepo.findById(id);
        if (empresaOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        Empresa empresa = empresaOptional.get();
        empresa.setNome(empresaDetails.getNome());
        empresa.setCnpj(empresaDetails.getCnpj());
        empresa.setEmail(empresaDetails.getEmail());
        empresa.setTelefone(empresaDetails.getTelefone());
        empresa.setEndereco(empresaDetails.getEndereco());
        
        Empresa updatedEmpresa = empresaRepo.save(empresa);
        return ResponseEntity.ok(updatedEmpresa);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmpresa(@PathVariable Long id) {
        if (!empresaRepo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        empresaRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}