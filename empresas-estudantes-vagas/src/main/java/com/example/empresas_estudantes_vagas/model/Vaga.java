package com.example.empresas_estudantes_vagas.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "vagas")
public class Vaga {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String titulo;
    
    private String descricao;
    private String requisitos;
    private String localizacao;
    private Double salario;
    
    @Column(nullable = false)
    private String status = "ABERTA";
    
    private LocalDateTime dataCriacao = LocalDateTime.now();
    
    @ManyToOne
    @JoinColumn(name = "empresa_id", nullable = false)
    private Empresa empresa;
    
    public Vaga() {}
    
    public Vaga(String titulo, String descricao, String requisitos, 
                String localizacao, Double salario, Empresa empresa) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.requisitos = requisitos;
        this.localizacao = localizacao;
        this.salario = salario;
        this.empresa = empresa;
    }
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public String getRequisitos() { return requisitos; }
    public void setRequisitos(String requisitos) { this.requisitos = requisitos; }
    public String getLocalizacao() { return localizacao; }
    public void setLocalizacao(String localizacao) { this.localizacao = localizacao; }
    public Double getSalario() { return salario; }
    public void setSalario(Double salario) { this.salario = salario; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDateTime dataCriacao) { this.dataCriacao = dataCriacao; }
    public Empresa getEmpresa() { return empresa; }
    public void setEmpresa(Empresa empresa) { this.empresa = empresa; }
}
