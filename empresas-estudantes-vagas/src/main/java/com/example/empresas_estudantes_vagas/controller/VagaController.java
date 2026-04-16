package com.example.empresasestudantesvagas.controller;

import com.example.empresasestudantesvagas.model.Vaga;
import com.example.empresasestudantesvagas.model.Empresa;
import com.example.empresasestudantesvagas.repository.VagaRepo;
import com.example.empresasestudantesvagas.repository.EmpresaRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/vagas")
public class VagaController {
    
    @Autowired
    private VagaRepo vagaRepo;
    
    @Autowired
    private EmpresaRepo empresaRepo;
    
    @GetMapping
    public List<Vaga> getAllVagas() {
        return vagaRepo.findAll();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Vaga> getVagaById(@PathVariable Long id) {
        Optional<Vaga> vaga = vagaRepo.findById(id);
        return vaga.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @GetMapping("/empresa/{empresaId}")
    public ResponseEntity<List<Vaga>> getVagasByEmpresa(@PathVariable Long empresaId) {
        Optional<Empresa> empresa = empresaRepo.findById(empresaId);
        if (empresa.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(vagaRepo.findByEmpresa(empresa.get()));
    }
    
    @GetMapping("/status/{status}")
    public List<Vaga> getVagasByStatus(@PathVariable String status) {
        return vagaRepo.findByStatus(status);
    }
    
    @PostMapping
    public ResponseEntity<Vaga> createVaga(@RequestBody Vaga vaga) {
        if (vaga.getEmpresa() == null || vaga.getEmpresa().getId() == null) {
            return ResponseEntity.badRequest().build();
        }
        
        Optional<Empresa> empresa = empresaRepo.findById(vaga.getEmpresa().getId());
        if (empresa.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        vaga.setEmpresa(empresa.get());
        Vaga savedVaga = vagaRepo.save(vaga);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedVaga);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Vaga> updateVaga(@PathVariable Long id, @RequestBody Vaga vagaDetails) {
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
        
        Vaga updatedVaga = vagaRepo.save(vaga);
        return ResponseEntity.ok(updatedVaga);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVaga(@PathVariable Long id) {
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
        Vaga updatedVaga = vagaRepo.save(vaga);
        return ResponseEntity.ok(updatedVaga);
    }
}