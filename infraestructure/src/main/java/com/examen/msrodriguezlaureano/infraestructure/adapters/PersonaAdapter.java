package com.examen.msrodriguezlaureano.infraestructure.adapters;


import com.examen.msrodriguezlaureano.domain.aggregates.constants.Constants;
import com.examen.msrodriguezlaureano.domain.aggregates.dto.PersonaDTO;
import com.examen.msrodriguezlaureano.domain.aggregates.request.RequestPersona;
import com.examen.msrodriguezlaureano.domain.aggregates.response.ResponseReniec;
import com.examen.msrodriguezlaureano.domain.ports.on.PersonaServiceOut;
import com.examen.msrodriguezlaureano.infraestructure.entity.PersonaEntity;
import com.examen.msrodriguezlaureano.infraestructure.entity.TipoDocumentoEntity;
import com.examen.msrodriguezlaureano.infraestructure.entity.TipoPersonaEntity;
import com.examen.msrodriguezlaureano.infraestructure.mapper.PersonaMapper;
import com.examen.msrodriguezlaureano.infraestructure.repository.PersonaRepository;
import com.examen.msrodriguezlaureano.infraestructure.repository.TipoDocumentoRepository;
import com.examen.msrodriguezlaureano.infraestructure.repository.TipoPersonaRepository;
import com.examen.msrodriguezlaureano.infraestructure.rest.client.ClienteReniec;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PersonaAdapter implements PersonaServiceOut {

    private final PersonaRepository personaRepository;
    private final TipoDocumentoRepository tipoDocumentoRepository;
    private final TipoPersonaRepository tipoPersonaRepository;
    private final PersonaMapper personaMapper;
    private final ClienteReniec reniec;

    @Value("${token.api}")
    private String tokenApi;


    @Override
    public PersonaDTO crearPersonaOut(RequestPersona requestPersona) {
        ResponseReniec datosReniec = getExecutionReniec(requestPersona.getNumDoc());
        personaRepository.save(getEntity(datosReniec,requestPersona));
        return personaMapper.mapToDto(getEntity(datosReniec,requestPersona));
    }

    @Override
    public Optional<PersonaDTO> obtenerPersonaNumDocOut(String numDoc) {
        return Optional.ofNullable(personaMapper.mapToDto(personaRepository.findByNumDocu(numDoc).get()));
    }

    @Override
    public List<PersonaDTO> obtenerTodosEstadoActivoOut() {
        List<PersonaDTO> personaDTOList = new ArrayList<>();
        List<PersonaEntity> entities = personaRepository.findByEstado(1);
        for(PersonaEntity persona : entities){
            PersonaDTO personaDTO = personaMapper.mapToDto(persona);
            personaDTOList.add(personaDTO);
        }
        return personaDTOList;
    }

    @Override
    public PersonaDTO actualizarOut(Long id, RequestPersona requestPersona) {
        boolean existe = personaRepository.existsById(id);
        if(existe){
            Optional<PersonaEntity> entity = personaRepository.findById(id);
            ResponseReniec responseReniec = getExecutionReniec(requestPersona.getNumDoc());
            personaRepository.save(getEntityUpdate(responseReniec,entity.get(), requestPersona.getTipoPer()));
            return personaMapper.mapToDto(getEntityUpdate(responseReniec, entity.get(),requestPersona.getTipoPer()));
        }
        return null;
    }

    @Override
    public PersonaDTO deleteOut(Long id) {
        boolean existe = personaRepository.existsById(id);
        if(existe){
            Optional<PersonaEntity> entity = personaRepository.findById(id);
            entity.get().setEstado(0);
            entity.get().setUsuaDelet(Constants.AUDIT_ADMIN);
            entity.get().setDateDelet(getTimestamp());
            personaRepository.save(entity.get());
            return personaMapper.mapToDto(entity.get());
        }
        return null;
    }

    public ResponseReniec getExecutionReniec(String numero){
        String authorization = "Bearer "+tokenApi;
        ResponseReniec responseReniec = reniec.getInfoReniec(numero,authorization);
        return  responseReniec;
    }
    private PersonaEntity getEntity(ResponseReniec reniec, RequestPersona requestPersona){
        TipoDocumentoEntity tipoDocumento = tipoDocumentoRepository.findByCodTipo(requestPersona.getTipoDoc());
        TipoPersonaEntity tipoPersona= tipoPersonaRepository.findByCodTipo(requestPersona.getTipoPer());
        PersonaEntity entity = new PersonaEntity();
        entity.setNumDocu(reniec.getNumeroDocumento());
        entity.setNombres(reniec.getNombres());
        entity.setApePat(reniec.getApellidoPaterno());
        entity.setApeMat(reniec.getApellidoMaterno());
        entity.setEstado(Constants.STATUS_ACTIVE);
        entity.setUsuaCrea(Constants.AUDIT_ADMIN);
        entity.setDateCreate(getTimestamp());
        entity.setTipoDocumento(tipoDocumento);
        entity.setTipoPersona(tipoPersona);
        return entity;
    }
    private PersonaEntity getEntityUpdate(ResponseReniec reniec, PersonaEntity personaActualizar, String codTipoPersona){
        TipoPersonaEntity tipoPersona= tipoPersonaRepository.findByCodTipo(codTipoPersona);
        personaActualizar.setNombres(reniec.getNombres());
        personaActualizar.setApePat(reniec.getApellidoPaterno());
        personaActualizar.setApeMat(reniec.getApellidoMaterno());
        personaActualizar.setUsuaModif(Constants.AUDIT_ADMIN);
        personaActualizar.setDateModif(getTimestamp());
        personaActualizar.setTipoPersona(tipoPersona);
        return personaActualizar;
    }
    private Timestamp getTimestamp(){
        long currentTime = System.currentTimeMillis();
        Timestamp timestamp = new Timestamp(currentTime);
        return timestamp;
    }
}
