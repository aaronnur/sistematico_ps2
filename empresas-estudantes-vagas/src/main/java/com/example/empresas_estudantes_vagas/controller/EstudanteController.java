package com.example.empresas_estudantes_vagas.controller;

import com.example.empresas_estudantes_vagas.model.Estudante;
import com.example.empresas_estudantes_vagas.repository.EstudanteRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/api/estudantes")
public class EstudanteController {
    
    @Autowired
    private EstudanteRepo estudanteRepo;
    
    @GetMapping
    public Iterable<Estudante> getAll() {
        return estudanteRepo.findAll();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Estudante> getById(@PathVariable Long id) {
        Optional<Estudante> estudante = estudanteRepo.findById(id);
        return estudante.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<Estudante> getByCpf(@PathVariable String cpf) {
        Optional<Estudante> estudante = estudanteRepo.findByCpf(cpf);
        return estudante.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/curso/{curso}")
    public Iterable<Estudante> getByCurso(@PathVariable String curso) {
        return estudanteRepo.findByCurso(curso);
    }
    
    @PostMapping
    public ResponseEntity<Estudante> create(@RequestBody Estudante estudante) {
        if (estudanteRepo.findByCpf(estudante.getCpf()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(estudanteRepo.save(estudante));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Estudante> update(@PathVariable Long id, @RequestBody Estudante estudanteDetails) {
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
        
        return ResponseEntity.ok(estudanteRepo.save(estudante));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!estudanteRepo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        estudanteRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
