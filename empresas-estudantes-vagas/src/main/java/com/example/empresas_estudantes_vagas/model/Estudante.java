package com.example.empresas_estudantes_vagas.model;

import java.time.LocalDate;

public class Estudante {
    private Long id;
    private String nome;
    private String email;
    private LocalDate nascimento;
    private Integer anoIngresso;
    
    public Estudante() {}
    
    public Estudante(Long id, String nome, String email, LocalDate nascimento, Integer anoIngresso) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.nascimento = nascimento;
        this.anoIngresso = anoIngresso;
    }
    
    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public LocalDate getNascimento() { return nascimento; }
    public void setNascimento(LocalDate nascimento) { this.nascimento = nascimento; }
    
    public Integer getAnoIngresso() { return anoIngresso; }
    public void setAnoIngresso(Integer anoIngresso) { this.anoIngresso = anoIngresso; }
}