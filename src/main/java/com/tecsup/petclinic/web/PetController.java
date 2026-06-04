package com.tecsup.petclinic.web;

import com.tecsup.petclinic.dtos.PetDTO;
import com.tecsup.petclinic.mappers.PetMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.tecsup.petclinic.entities.Pet;
import com.tecsup.petclinic.exceptions.PetNotFoundException;
import com.tecsup.petclinic.service.PetService;

import java.util.List;

/**
 *
 * @author jgomezm
 *
 */
@RestController
@Slf4j
public class PetController {

    String name = null;

    //@Autowired
    private PetService petService;

    //@Autowired
    private PetMapper mapper;

    /**
     *  Change
     * @param petService
     * @param mapper
     */
    public PetController(PetService petService, PetMapper mapper){
        this.petService = petService;
        this.mapper = mapper ;
    }

    /**
     * Get all pets
     *
     * @return
     */
    @GetMapping(value = "/pets")
    public ResponseEntity<List<PetDTO>> findAllPets() {

        List<Pet> pets = petService.findAll();
        log.info("pets: " + pets);
        pets.forEach(item -> log.info("Pet >>  {} ", item));

        List<PetDTO> petsTO = this.mapper.mapToDtoList(pets);
        log.info("petsTO: " + petsTO);
        petsTO.forEach(item -> log.info("PetTO >>  {} ", item));

        return ResponseEntity.ok(petsTO);

    }


    /**
     * Create pet
     *
     * @param petTO
     * @return
     */
    @PostMapping(value = "/pets")
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<PetDTO> create(@RequestBody PetDTO petTO) {

        //Pet newPet = this.mapper.mapToEntity(petTO);
        PetDTO newPetTO = petService.create(petTO);

        return  ResponseEntity.status(HttpStatus.CREATED).body(newPetTO);

    }


    /**
     * Find pet by id
     *
     * @param id
     * @return
     * @throws PetNotFoundException
     */
    @GetMapping(value = "/pets/{id}")
    ResponseEntity<PetDTO> findById(@PathVariable Integer id) {

        PetDTO petDto = null;

        try {
            petDto = petService.findById(id);

        } catch (PetNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(petDto);
    }

    /**
     * Update and create pet
     *
     * @param petTO
     * @param id
     * @return
     */
    @PutMapping(value = "/pets/{id}")
    ResponseEntity<PetDTO>  update(@RequestBody PetDTO petTO, @PathVariable Integer id) {

        PetDTO updatePetDto = null;

        try {

            updatePetDto = petService.findById(id);

            updatePetDto.setName(petTO.getName());
            updatePetDto.setOwnerId(petTO.getOwnerId());
            updatePetDto.setTypeId(petTO.getTypeId());

            petService.update(updatePetDto);

        } catch (PetNotFoundException e) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(updatePetDto);
    }

    /**
     * Delete pet by id
     *
     * @param id
     */
    @DeleteMapping(value = "/pets/{id}")
    ResponseEntity<String> delete(@PathVariable Integer id) {

        try {
            petService.delete(id);
            return ResponseEntity.ok(" Delete ID :" + id);
        } catch (PetNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
