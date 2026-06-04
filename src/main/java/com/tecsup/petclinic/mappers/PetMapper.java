package com.tecsup.petclinic.mappers;

import com.tecsup.petclinic.dtos.PetDTO;
import com.tecsup.petclinic.entities.Pet;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.factory.Mappers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Mapper(componentModel = "spring", nullValueMappingStrategy =  NullValueMappingStrategy.RETURN_DEFAULT)
public interface PetMapper {

    PetMapper INSTANCE = Mappers.getMapper(PetMapper.class);

    //@Mapping(target = "name", source = "name")
    @Mapping(source = "birthDate", target = "birthDate")
    Pet mapToEntity(PetDTO petTO);

    default Date stringToDate(String dateStr) {
        System.out.println("Converting string to date: " + dateStr);

        if (dateStr == null || dateStr.isEmpty()) {
            return null;
        }

        Date date = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = dateFormat.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    @Mapping(source = "birthDate", target = "birthDate")
    PetDTO mapToDto(Pet pet);

    default String dateToString(Date date) {

        if (date != null ) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            return dateFormat.format(date);
        } else {
            return "";
        }

    }

    List<PetDTO> mapToDtoList(List<Pet> petList);

    List<Pet> mapToEntityList(List<PetDTO> petTOList);


}
