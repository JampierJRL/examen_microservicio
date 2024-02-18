package com.examen.msrodriguezlaureano.domain.ports.on;

import com.examen.msrodriguezlaureano.domain.aggregates.dto.PersonaDTO;
import com.examen.msrodriguezlaureano.domain.aggregates.request.RequestPersona;

import java.util.List;
import java.util.Optional;

public interface PersonaServiceOut {
    PersonaDTO crearPersonaOut(RequestPersona requestPersona);
    Optional<PersonaDTO> obtenerPersonaNumDocOut(String numDoc);
    List<PersonaDTO> obtenerTodosEstadoActivoOut();
    PersonaDTO actualizarOut(Long id, RequestPersona requestPersona);
    PersonaDTO deleteOut(Long id);
}
