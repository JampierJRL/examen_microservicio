package com.examen.msrodriguezlaureano.domain.ports.in;

import com.examen.msrodriguezlaureano.domain.aggregates.dto.PersonaDTO;
import com.examen.msrodriguezlaureano.domain.aggregates.request.RequestPersona;

import java.util.List;
import java.util.Optional;

public interface PersonaServiceIn {
    PersonaDTO crearPersonaIn(RequestPersona requestPersona);
    Optional<PersonaDTO> obtenerPersonaNumDocIn(String numDoc);
    List<PersonaDTO> obtenerTodosEstadoActivoIn();
    PersonaDTO actualizarIn(Long id, RequestPersona requestPersona);
    PersonaDTO deleteIn(Long id);
}
