// src/main/java/com/tecsup/petclinic/mappers/VetMapper.java
package com.tecsup.petclinic.mappers;

import com.tecsup.petclinic.entities.Vet;
import com.tecsup.petclinic.dtos.VetDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface VetMapper {
    @Mappings({
            @Mapping(source = "firstName", target = "firstName"),
            @Mapping(source = "isActive", target = "active")
    })
    Vet toEntity(VetDTO vetDTO);

    @Mappings({
            @Mapping(source = "active", target = "isActive")
    })
    VetDTO toDTO(Vet vet);
}
