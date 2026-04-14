package com.example.empresas_estudantes_vagas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EmpresasEstudantesVagasApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmpresasEstudantesVagasApplication.class, args);
        System.out.println("=== API Gestão de Empresas, Estudantes e Vagas ===");
        System.out.println("Servidor iniciado em: http://localhost:8080");
        System.out.println("Endpoints disponíveis:");
        System.out.println("  - GET    /api/empresas");
        System.out.println("  - GET    /api/estudantes");
        System.out.println("  - GET    /api/vagas");
    }
}