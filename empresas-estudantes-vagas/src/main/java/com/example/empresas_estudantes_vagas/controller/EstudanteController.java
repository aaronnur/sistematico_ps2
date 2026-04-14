package com.example.empresas_estudantes_vagas.controller;

import com.example.empresas_estudantes_vagas.model.Estudante;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/estudantes")
public class EstudanteController {
    
    private List<Estudante> estudantes = new ArrayList<>();
    private Long nextId = 11L;
    
    public EstudanteController() {
        estudantes.add(new Estudante(1L, "Ana Paula Souza", "ana.souza@email.com", LocalDate.parse("2002-03-15"), 2020));
        estudantes.add(new Estudante(2L, "Carlos Henrique Lima", "carlos.lima@email.com", LocalDate.parse("2001-10-22"), 2019));
        estudantes.add(new Estudante(3L, "Fernanda Oliveira", "fernanda.oliveira@email.com", LocalDate.parse("2003-07-05"), 2021));
        estudantes.add(new Estudante(4L, "Lucas Pereira", "lucas.pereira@email.com", LocalDate.parse("2002-04-11"), 2020));
        estudantes.add(new Estudante(5L, "Gabriela Martins", "gabriela.martins@email.com", LocalDate.parse("2001-12-25"), 2019));
        estudantes.add(new Estudante(6L, "Rafael Costa", "rafael.costa@email.com", LocalDate.parse("2000-09-13"), 2018));
        estudantes.add(new Estudante(7L, "Juliana Silva", "juliana.silva@email.com", LocalDate.parse("2002-06-18"), 2020));
        estudantes.add(new Estudante(8L, "Marcos Vinícius", "marcos.vinicius@email.com", LocalDate.parse("2003-01-30"), 2021));
        estudantes.add(new Estudante(9L, "Camila Azevedo", "camila.azevedo@email.com", LocalDate.parse("2001-11-08"), 2019));
        estudantes.add(new Estudante(10L, "Felipe Cardoso", "felipe.cardoso@email.com", LocalDate.parse("2000-08-27"), 2018));
    }
    
    @GetMapping
    public List<Estudante> getAll() {
        return estudantes;
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Estudante> getById(@PathVariable Long id) {
        Optional<Estudante> estudante = estudantes.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst();
        return estudante.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<Estudante> create(@RequestBody Estudante estudante) {
        estudante.setId(nextId++);
        estudantes.add(estudante);
        return ResponseEntity.status(HttpStatus.CREATED).body(estudante);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Estudante> update(@PathVariable Long id, @RequestBody Estudante estudanteAtualizado) {
        Optional<Estudante> estudanteExistente = estudantes.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst();
        
        if (estudanteExistente.isPresent()) {
            Estudante estudante = estudanteExistente.get();
            estudante.setNome(estudanteAtualizado.getNome());
            estudante.setEmail(estudanteAtualizado.getEmail());
            estudante.setNascimento(estudanteAtualizado.getNascimento());
            estudante.setAnoIngresso(estudanteAtualizado.getAnoIngresso());
            return ResponseEntity.ok(estudante);
        }
        return ResponseEntity.notFound().build();
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean removed = estudantes.removeIf(e -> e.getId().equals(id));
        return removed ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}