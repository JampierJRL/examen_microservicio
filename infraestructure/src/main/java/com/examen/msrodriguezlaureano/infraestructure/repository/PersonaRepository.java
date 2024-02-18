package com.examen.msrodriguezlaureano.infraestructure.repository;

import com.examen.msrodriguezlaureano.infraestructure.entity.PersonaEntity;
import org.aspectj.weaver.patterns.PerObject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PersonaRepository extends JpaRepository<PersonaEntity, Long> {
    List<PersonaEntity> findByEstado(Integer estado);
    Optional<PersonaEntity> findByNumDocu(String numDocumento);
}
