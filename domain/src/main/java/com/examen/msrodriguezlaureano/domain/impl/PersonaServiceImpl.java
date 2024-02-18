package com.examen.msrodriguezlaureano.domain.impl;

import com.examen.msrodriguezlaureano.domain.aggregates.dto.PersonaDTO;
import com.examen.msrodriguezlaureano.domain.aggregates.request.RequestPersona;
import com.examen.msrodriguezlaureano.domain.ports.in.PersonaServiceIn;
import com.examen.msrodriguezlaureano.domain.ports.on.PersonaServiceOut;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PersonaServiceImpl implements PersonaServiceIn {

    private final PersonaServiceOut personaServiceOut;
    @Override
    public PersonaDTO crearPersonaIn(RequestPersona requestPersona) {
        return personaServiceOut.crearPersonaOut(requestPersona);
    }

    @Override
    public Optional<PersonaDTO> obtenerPersonaNumDocIn(String numDoc) {
        return personaServiceOut.obtenerPersonaNumDocOut(numDoc);
    }

    @Override
    public List<PersonaDTO> obtenerTodosEstadoActivoIn() {
        return personaServiceOut.obtenerTodosEstadoActivoOut();
    }

    @Override
    public PersonaDTO actualizarIn(Long id, RequestPersona requestPersona) {
        return personaServiceOut.actualizarOut(id,requestPersona);
    }

    @Override
    public PersonaDTO deleteIn(Long id) {
        return personaServiceOut.deleteOut(id);
    }
}
