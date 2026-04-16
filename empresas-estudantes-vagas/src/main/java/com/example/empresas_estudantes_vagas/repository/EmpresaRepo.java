package com.example.empresas_estudantes_vagas.repository;

import com.example.empresas_estudantes_vagas.model.Empresa;
import org.springframework.data.repository.CrudRepository;

public interface EmpresaRepo extends CrudRepository<Empresa, Long> {
}
