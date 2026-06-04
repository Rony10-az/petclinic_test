package com.tecsup.petclinic.service;

import com.tecsup.petclinic.dtos.PetDTO;
import com.tecsup.petclinic.entities.Pet;
import com.tecsup.petclinic.exceptions.PetNotFoundException;
import com.tecsup.petclinic.service.PetService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
public class PetServiceTest {

    @Autowired
    private PetService petService ;

    @Test
    public void testFindPetById() {

        final String NAME_EXPECTED = "Leo";

        Integer ID = 1;

        PetDTO pet = null;

        try {
            pet = this.petService.findById(ID);
        } catch (PetNotFoundException e) {
            fail(e.getMessage());
        }
        assertEquals(NAME_EXPECTED, pet.getName());
    }

    /**
     *
     */
    @Test
    public void testFindPetByName() {

        String FIND_NAME = "Leo";
        int SIZE_EXPECTED = 1;

        List<PetDTO> pets = this.petService.findByName(FIND_NAME);

        assertEquals(SIZE_EXPECTED, pets.size());
    }

    /**
     *
     */
    @Test
    public void testFindPetByTypeId() {

        int TYPE_ID = 5;
        int SIZE_EXPECTED = 2;

        List<Pet> pets = this.petService.findByTypeId(TYPE_ID);

        assertEquals(SIZE_EXPECTED, pets.size());
    }

    /**
     *
     */
    @Test
    public void testFindPetByOwnerId() {

        int OWNER_ID = 10;
        int SIZE_EXPECTED = 2;

        List<Pet> pets = this.petService.findByOwnerId(OWNER_ID);

        assertEquals(SIZE_EXPECTED, pets.size());

    }

    /**
     *
     */
    @Test
    public void testCreatePet() {

        String PET_NAME = "Ponky";
        int OWNER_ID = 1;
        int TYPE_ID = 1;

        PetDTO petDTO = PetDTO.builder()
                .name(PET_NAME)
                .ownerId(OWNER_ID)
                .typeId(TYPE_ID)
                .build();


        PetDTO newPetDTO = this.petService.create(petDTO);

        log.info("PET CREATED :" + newPetDTO.toString());

        assertNotNull(newPetDTO.getId());
        assertEquals(PET_NAME, newPetDTO.getName());
        assertEquals(OWNER_ID, newPetDTO.getOwnerId());
        assertEquals(TYPE_ID, newPetDTO.getTypeId());

    }


    /**
     *
     */
    @Test
    public void testUpdatePet() {

        String PET_NAME = "Bear";
        int OWNER_ID = 1;
        int TYPE_ID = 1;

        String UP_PET_NAME = "Bear2";
        int UP_OWNER_ID = 2;
        int UP_TYPE_ID = 2;

        PetDTO petDTO = PetDTO.builder()
                .name(PET_NAME)
                .ownerId(OWNER_ID)
                .typeId(TYPE_ID)
                .build();

        // ------------ Create ---------------

        log.info(">" + petDTO);
        PetDTO petDTOCreated = this.petService.create(petDTO);
        log.info(">>" + petDTOCreated);

        // ------------ Update ---------------

        // Prepare data for update
        petDTOCreated.setName(UP_PET_NAME);
        petDTOCreated.setOwnerId(UP_OWNER_ID);
        petDTOCreated.setTypeId(UP_TYPE_ID);

        // Execute update
        PetDTO upgradePetDTO = this.petService.update(petDTOCreated);
        log.info(">>>>" + upgradePetDTO);

        //            EXPECTED        ACTUAL
        assertEquals(UP_PET_NAME, upgradePetDTO.getName());
        assertEquals(UP_OWNER_ID, upgradePetDTO.getTypeId());
        assertEquals(UP_TYPE_ID, upgradePetDTO.getOwnerId());
    }

    /**
     *
     */
    @Test
    public void testDeletePet() {

        String PET_NAME = "Bird";
        int OWNER_ID = 1;
        int TYPE_ID = 1;

        // ------------ Create ---------------

        PetDTO petDTO = PetDTO.builder()
                .name(PET_NAME)
                .ownerId(OWNER_ID)
                .typeId(TYPE_ID)
                .build();

        PetDTO  newPetDTO = this.petService.create(petDTO);
        log.info("" + petDTO);

        // ------------ Delete ---------------

        try {
            this.petService.delete(newPetDTO.getId());
        } catch (PetNotFoundException e) {
            fail(e.getMessage());
        }

        // ------------ Validation ---------------

        try {
            this.petService.findById(newPetDTO.getId());
            assertTrue(false);
        } catch (PetNotFoundException e) {
            assertTrue(true);
        }

    }
}
