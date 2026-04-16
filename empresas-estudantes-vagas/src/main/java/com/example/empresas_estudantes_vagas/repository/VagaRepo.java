package com.example.empresas_estudantes_vagas.repository;

import com.example.empresas_estudantes_vagas.model.Vaga;
import com.example.empresas_estudantes_vagas.model.Empresa;
import org.springframework.data.repository.CrudRepository;

public interface VagaRepo extends CrudRepository<Vaga, Long> {
    Iterable<Vaga> findByEmpresa(Empresa empresa);
    Iterable<Vaga> findByStatus(String status);
    Iterable<Vaga> findByLocalizacao(String localizacao);
    Iterable<Vaga> findBySalarioGreaterThanEqual(Double salario);
}
