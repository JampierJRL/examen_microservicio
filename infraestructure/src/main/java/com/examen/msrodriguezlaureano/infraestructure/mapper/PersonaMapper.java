package com.examen.msrodriguezlaureano.infraestructure.mapper;

import com.examen.msrodriguezlaureano.domain.aggregates.dto.PersonaDTO;
import com.examen.msrodriguezlaureano.infraestructure.entity.PersonaEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class PersonaMapper {

    private static final ModelMapper modelMapper = new ModelMapper();

    public PersonaDTO mapToDto(PersonaEntity personaEntity){
        return modelMapper.map(personaEntity, PersonaDTO.class);
    }

    public PersonaEntity mapToEntity(PersonaDTO personaDTO){
        return modelMapper.map(personaDTO, PersonaEntity.class);
    }

}
