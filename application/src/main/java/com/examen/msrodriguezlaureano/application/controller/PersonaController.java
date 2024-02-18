package com.examen.msrodriguezlaureano.application.controller;

import com.examen.msrodriguezlaureano.domain.aggregates.dto.PersonaDTO;
import com.examen.msrodriguezlaureano.domain.aggregates.request.RequestPersona;
import com.examen.msrodriguezlaureano.domain.ports.in.PersonaServiceIn;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.patterns.PerObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@OpenAPIDefinition(
        info = @Info(
                title = "API-PERSONA",
                version = "1.0",
                description = "Mantenimiento de una Persona"
        )
)
@RestController
@RequestMapping("/v2/persona")
@RequiredArgsConstructor
public class PersonaController {

    private final PersonaServiceIn personaServiceIn;

    @Operation(summary = "Crear Persona")
    @PostMapping
    public ResponseEntity<PersonaDTO> registrar(@RequestBody RequestPersona requestPersona){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(personaServiceIn.crearPersonaIn(requestPersona));
    }

    @Operation(summary = "Obtener Persona por su numero documento")
    @GetMapping("/{numDoc}")
    public ResponseEntity<PersonaDTO>obtenerPersona(@PathVariable String numDoc){

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(personaServiceIn.obtenerPersonaNumDocIn(numDoc).get());

    }

    @Operation(summary = "Obtener todas las personas con estado=1 activo")
    @GetMapping()
    public ResponseEntity<List<PersonaDTO>>obtenerTodosEstadoActivo(){

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(personaServiceIn.obtenerTodosEstadoActivoIn());

    }
    @Operation(summary = "Actualizar persona")
    @PutMapping("/{id}")
    public ResponseEntity<PersonaDTO>actualizar(@PathVariable Long id,@RequestBody RequestPersona requestPersona){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(personaServiceIn.actualizarIn(id,requestPersona));

    }

    @Operation(summary = "Eliminar Persona")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<PersonaDTO>eliminar(@PathVariable Long id){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(personaServiceIn.deleteIn(id));
    }
}
