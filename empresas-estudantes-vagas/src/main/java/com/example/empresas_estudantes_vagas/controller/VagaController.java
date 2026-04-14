package com.example.empresas_estudantes_vagas.controller;

import com.example.empresas_estudantes_vagas.model.Vaga;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/vagas")
public class VagaController {
    
    private List<Vaga> vagas = new ArrayList<>();
    private Long nextId = 8L;
    
    public VagaController() {
        vagas.add(new Vaga(1L, "Desenvolvedor Java", "Atuação em projetos backend com Java e Spring. Experiência desejada em APIs REST.", LocalDate.parse("2025-10-01"), true, 1L));
        vagas.add(new Vaga(2L, "Analista de Suporte Técnico", "Suporte a clientes, resolução de chamados e participação em treinamentos internos.", LocalDate.parse("2025-09-27"), true, 2L));
        vagas.add(new Vaga(3L, "Engenheiro de Software", "Desenvolvimento de soluções para sistemas corporativos, integração e automação.", LocalDate.parse("2025-10-03"), false, 3L));
        vagas.add(new Vaga(4L, "Analista de Dados", "Manipulação e análise de grandes volumes de dados. Conhecimentos de SQL e Python.", LocalDate.parse("2025-09-18"), true, 4L));
        vagas.add(new Vaga(5L, "Designer Digital", "Criação de materiais gráficos, UX/UI e participação em campanhas de marketing.", LocalDate.parse("2025-09-30"), false, 5L));
        vagas.add(new Vaga(6L, "Consultor de Projetos", "Elaboração e acompanhamento de projetos empresariais e treinamentos.", LocalDate.parse("2025-10-06"), true, 1L));
        vagas.add(new Vaga(7L, "Programador Full Stack", "Desenvolvimento de aplicações web frontend e backend com foco em automação.", LocalDate.parse("2025-10-04"), true, 2L));
    }
    
    @GetMapping
    public List<Vaga> getAll() {
        return vagas;
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Vaga> getById(@PathVariable Long id) {
        Optional<Vaga> vaga = vagas.stream()
                .filter(v -> v.getId().equals(id))
                .findFirst();
        return vaga.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/empresa/{idEmpresa}")
    public List<Vaga> getByEmpresa(@PathVariable Long idEmpresa) {
        return vagas.stream()
                .filter(v -> v.getIdEmpresa().equals(idEmpresa))
                .toList();
    }
    
    @PostMapping
    public ResponseEntity<Vaga> create(@RequestBody Vaga vaga) {
        vaga.setId(nextId++);
        vagas.add(vaga);
        return ResponseEntity.status(HttpStatus.CREATED).body(vaga);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Vaga> update(@PathVariable Long id, @RequestBody Vaga vagaAtualizada) {
        Optional<Vaga> vagaExistente = vagas.stream()
                .filter(v -> v.getId().equals(id))
                .findFirst();
        
        if (vagaExistente.isPresent()) {
            Vaga vaga = vagaExistente.get();
            vaga.setTitulo(vagaAtualizada.getTitulo());
            vaga.setDescricao(vagaAtualizada.getDescricao());
            vaga.setPublicacao(vagaAtualizada.getPublicacao());
            vaga.setAtivo(vagaAtualizada.getAtivo());
            vaga.setIdEmpresa(vagaAtualizada.getIdEmpresa());
            return ResponseEntity.ok(vaga);
        }
        return ResponseEntity.notFound().build();
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean removed = vagas.removeIf(v -> v.getId().equals(id));
        return removed ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}