package com.tecsup.petclinic.mappers;

import com.tecsup.petclinic.dtos.PetDTO;
import com.tecsup.petclinic.entities.Pet;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PetMapper {

    Pet mapToEntity(PetDTO dto);

    PetDTO mapToDto(Pet entity);
}
