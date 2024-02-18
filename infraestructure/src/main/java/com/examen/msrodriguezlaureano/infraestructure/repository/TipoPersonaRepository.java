package com.examen.msrodriguezlaureano.infraestructure.repository;

import com.examen.msrodriguezlaureano.infraestructure.entity.TipoDocumentoEntity;
import com.examen.msrodriguezlaureano.infraestructure.entity.TipoPersonaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface TipoPersonaRepository extends JpaRepository <TipoPersonaEntity,Long> {

    TipoPersonaEntity findByCodTipo(@Param("codTipo") String codTipo);
}
