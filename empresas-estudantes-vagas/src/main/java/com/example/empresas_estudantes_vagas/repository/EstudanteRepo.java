package com.example.empresasestudantesvagas.repository;

import com.example.empresasestudantesvagas.model.Estudante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface EstudanteRepo extends JpaRepository<Estudante, Long> {
    Optional<Estudante> findByCpf(String cpf);
    List<Estudante> findByCurso(String curso);
    List<Estudante> findByInstituicao(String instituicao);
}