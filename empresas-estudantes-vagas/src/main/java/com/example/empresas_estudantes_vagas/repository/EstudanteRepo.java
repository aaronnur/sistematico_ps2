package com.example.empresas_estudantes_vagas.repository;

import com.example.empresas_estudantes_vagas.model.Estudante;
import org.springframework.data.repository.CrudRepository;
import java.util.Optional;

public interface EstudanteRepo extends CrudRepository<Estudante, Long> {
    Optional<Estudante> findByCpf(String cpf);
    Iterable<Estudante> findByCurso(String curso);
    Iterable<Estudante> findByInstituicao(String instituicao);
}
