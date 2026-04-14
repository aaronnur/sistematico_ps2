package com.example.gestaoempregos.controller;

import com.example.gestaoempregos.model.Empresa;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/empresas")
public class EmpresaController {
    
    private List<Empresa> empresas = new ArrayList<>();
    private Long nextId = 6L;
    
    public EmpresaController() {
        // Dados iniciais
        empresas.add(new Empresa(1L, "Empresa Alfa LTDA", "12.345.678/0001-90", "contato@empresa-alfa.com"));
        empresas.add(new Empresa(2L, "Beta Comércio ME", "98.765.432/0001-10", "beta@comercio.com"));
        empresas.add(new Empresa(3L, "Gamma Serviços S.A.", "11.222.333/0001-44", "servicos@gamma.com"));
        empresas.add(new Empresa(4L, "Delta Engenharia", "22.333.444/0001-55", "contato@deltaeng.com"));
        empresas.add(new Empresa(5L, "Epsilon Digital", "33.444.555/0001-66", "email@epsilondigital.com"));
    }
    
    @GetMapping
    public List<Empresa> getAll() {
        return empresas;
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Empresa> getById(@PathVariable Long id) {
        Optional<Empresa> empresa = empresas.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst();
        
        return empresa.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<Empresa> create(@RequestBody Empresa empresa) {
        empresa.setId(nextId++);
        empresas.add(empresa);
        return ResponseEntity.status(HttpStatus.CREATED).body(empresa);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Empresa> update(@PathVariable Long id, @RequestBody Empresa empresaAtualizada) {
        Optional<Empresa> empresaExistente = empresas.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst();
        
        if (empresaExistente.isPresent()) {
            Empresa empresa = empresaExistente.get();
            empresa.setNome(empresaAtualizada.getNome());
            empresa.setCnpj(empresaAtualizada.getCnpj());
            empresa.setEmailContato(empresaAtualizada.getEmailContato());
            return ResponseEntity.ok(empresa);
        }
        
        return ResponseEntity.notFound().build();
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean removed = empresas.removeIf(e -> e.getId().equals(id));
        
        if (removed) {
            return ResponseEntity.noContent().build();
        }
        
        return ResponseEntity.notFound().build();
    }
}