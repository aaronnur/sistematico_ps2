package com.example.empresas_estudantes_vagas.controller;

import com.example.empresas_estudantes_vagas.model.Vaga;
import com.example.empresas_estudantes_vagas.model.Empresa;
import com.example.empresas_estudantes_vagas.repository.VagaRepo;
import com.example.empresas_estudantes_vagas.repository.EmpresaRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/api/vagas")
public class VagaController {
    
    @Autowired
    private VagaRepo vagaRepo;
    
    @Autowired
    private EmpresaRepo empresaRepo;
    
    @GetMapping
    public Iterable<Vaga> getAll() {
        return vagaRepo.findAll();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Vaga> getById(@PathVariable Long id) {
        Optional<Vaga> vaga = vagaRepo.findById(id);
        return vaga.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/empresa/{empresaId}")
    public ResponseEntity<Iterable<Vaga>> getByEmpresa(@PathVariable Long empresaId) {
        Optional<Empresa> empresa = empresaRepo.findById(empresaId);
        if (empresa.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(vagaRepo.findByEmpresa(empresa.get()));
    }
    
    @GetMapping("/status/{status}")
    public Iterable<Vaga> getByStatus(@PathVariable String status) {
        return vagaRepo.findByStatus(status);
    }
    
    @PostMapping
    public ResponseEntity<Vaga> create(@RequestBody Vaga vaga) {
        if (vaga.getEmpresa() == null || vaga.getEmpresa().getId() == null) {
            return ResponseEntity.badRequest().build();
        }
        
        Optional<Empresa> empresa = empresaRepo.findById(vaga.getEmpresa().getId());
        if (empresa.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        vaga.setEmpresa(empresa.get());
        return ResponseEntity.status(HttpStatus.CREATED).body(vagaRepo.save(vaga));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Vaga> update(@PathVariable Long id, @RequestBody Vaga vagaDetails) {
        Optional<Vaga> vagaOptional = vagaRepo.findById(id);
        if (vagaOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        Vaga vaga = vagaOptional.get();
        vaga.setTitulo(vagaDetails.getTitulo());
        vaga.setDescricao(vagaDetails.getDescricao());
        vaga.setRequisitos(vagaDetails.getRequisitos());
        vaga.setLocalizacao(vagaDetails.getLocalizacao());
        vaga.setSalario(vagaDetails.getSalario());
        vaga.setStatus(vagaDetails.getStatus());
        
        return ResponseEntity.ok(vagaRepo.save(vaga));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!vagaRepo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        vagaRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    
    @PatchMapping("/{id}/fechar")
    public ResponseEntity<Vaga> closeVaga(@PathVariable Long id) {
        Optional<Vaga> vagaOptional = vagaRepo.findById(id);
        if (vagaOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        Vaga vaga = vagaOptional.get();
        vaga.setStatus("FECHADA");
        return ResponseEntity.ok(vagaRepo.save(vaga));
    }
}
