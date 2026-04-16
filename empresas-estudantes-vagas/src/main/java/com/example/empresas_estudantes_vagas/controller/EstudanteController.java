package com.example.empresasestudantesvagas.controller;

import com.example.empresasestudantesvagas.model.Estudante;
import com.example.empresasestudantesvagas.repository.EstudanteRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/estudantes")
public class EstudanteController {
    
    @Autowired
    private EstudanteRepo estudanteRepo;
    
    @GetMapping
    public List<Estudante> getAllEstudantes() {
        return estudanteRepo.findAll();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Estudante> getEstudanteById(@PathVariable Long id) {
        Optional<Estudante> estudante = estudanteRepo.findById(id);
        return estudante.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<Estudante> getEstudanteByCpf(@PathVariable String cpf) {
        Optional<Estudante> estudante = estudanteRepo.findByCpf(cpf);
        return estudante.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @GetMapping("/curso/{curso}")
    public List<Estudante> getEstudantesByCurso(@PathVariable String curso) {
        return estudanteRepo.findByCurso(curso);
    }
    
    @PostMapping
    public ResponseEntity<Estudante> createEstudante(@RequestBody Estudante estudante) {
        if (estudanteRepo.findByCpf(estudante.getCpf()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        Estudante savedEstudante = estudanteRepo.save(estudante);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEstudante);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Estudante> updateEstudante(@PathVariable Long id, @RequestBody Estudante estudanteDetails) {
        Optional<Estudante> estudanteOptional = estudanteRepo.findById(id);
        if (estudanteOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        Estudante estudante = estudanteOptional.get();
        estudante.setNome(estudanteDetails.getNome());
        estudante.setCpf(estudanteDetails.getCpf());
        estudante.setEmail(estudanteDetails.getEmail());
        estudante.setTelefone(estudanteDetails.getTelefone());
        estudante.setCurso(estudanteDetails.getCurso());
        estudante.setInstituicao(estudanteDetails.getInstituicao());
        estudante.setSemestre(estudanteDetails.getSemestre());
        
        Estudante updatedEstudante = estudanteRepo.save(estudante);
        return ResponseEntity.ok(updatedEstudante);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEstudante(@PathVariable Long id) {
        if (!estudanteRepo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        estudanteRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}