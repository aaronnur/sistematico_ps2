package com.example.empresasestudantesvagas.repository;

import com.example.empresasestudantesvagas.model.Vaga;
import com.example.empresasestudantesvagas.model.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface VagaRepo extends JpaRepository<Vaga, Long> {
    List<Vaga> findByEmpresa(Empresa empresa);
    List<Vaga> findByStatus(String status);
    List<Vaga> findByLocalizacao(String localizacao);
    List<Vaga> findBySalarioGreaterThanEqual(Double salario);
}