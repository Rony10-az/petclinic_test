package com.tecsup.petclinic.service;

import com.tecsup.petclinic.dtos.PetDTO;
import com.tecsup.petclinic.entities.Pet;
import com.tecsup.petclinic.exceptions.PetNotFoundException;
import com.tecsup.petclinic.mappers.PetMapper;
import com.tecsup.petclinic.repository.PetRepository;
import com.tecsup.petclinic.util.TObjectCreator;

import lombok.extern.slf4j.Slf4j;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
public class PetServiceMockitoTest {

    @Autowired
    private PetService petService;

    @Autowired
    private PetMapper petMapper;

    @MockitoBean
    private PetRepository repository;

    // ========================= FIND BY ID =========================
    @Test
    public void testFindPetById() {

        Pet petExpected = new Pet(1, "Leo", 1, 1, null, null);

        Mockito.when(repository.findById(1))
                .thenReturn(Optional.of(petExpected));

        PetDTO pet = null;

        try {
            pet = petService.findById(1);
        } catch (PetNotFoundException e) {
            fail(e.getMessage());
        }

        assertNotNull(pet);
        assertEquals(petExpected.getId(), pet.getId());
        assertEquals(petExpected.getName(), pet.getName());
    }

    // ========================= FIND BY NAME =========================
    @Test
    public void testFindPetByName() {

        String FIND_NAME = "Leo";
        List<Pet> petsExpected = TObjectCreator.getPetsForFindByName();

        Mockito.when(repository.findByName(FIND_NAME))
                .thenReturn(petsExpected);

        List<PetDTO> pets = petService.findByName(FIND_NAME);

        assertEquals(petsExpected.size(), pets.size());
    }

    // ========================= FIND BY TYPE =========================
    @Test
    public void testFindPetByTypeId() {

        int TYPE_ID = 5;
        List<Pet> petsExpected = TObjectCreator.getPetsForFindByTypeId();

        Mockito.when(repository.findByTypeId(TYPE_ID))
                .thenReturn(petsExpected);

        List<Pet> pets = petService.findByTypeId(TYPE_ID);

        assertEquals(petsExpected.size(), pets.size());
    }

    // ========================= FIND BY OWNER =========================
    @Test
    public void testFindPetByOwnerId() {

        int OWNER_ID = 10;
        List<Pet> petsExpected = TObjectCreator.getPetsForFindByOwnerId();

        Mockito.when(repository.findByOwnerId(OWNER_ID))
                .thenReturn(petsExpected);

        List<Pet> pets = petService.findByOwnerId(OWNER_ID);

        assertEquals(petsExpected.size(), pets.size());
    }

    // ========================= CREATE =========================
    @Test
    public void testCreatePet() {

        Pet newPet = TObjectCreator.newPet();
        Pet newPetCreated = TObjectCreator.newPetCreated();

        PetDTO newPetDTO = petMapper.mapToDto(newPet);
        PetDTO expectedDTO = petMapper.mapToDto(newPetCreated);

        Mockito.when(repository.save(newPet))
                .thenReturn(newPetCreated);

        PetDTO result = petService.create(newPetDTO);

        assertNotNull(result.getId());
        assertEquals(expectedDTO.getName(), result.getName());
        assertEquals(expectedDTO.getOwnerId(), result.getOwnerId());
        assertEquals(expectedDTO.getTypeId(), result.getTypeId());
    }

    // ========================= UPDATE =========================
    @Test
    public void testUpdatePet() {

        // Arrange
        Pet petOriginal = TObjectCreator.newPetForUpdate();
        PetDTO dto = petMapper.mapToDto(petOriginal);

        Mockito.when(repository.save(Mockito.any(Pet.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        dto.setName("Bear2");
        dto.setOwnerId(2);
        dto.setTypeId(2);

        PetDTO result = petService.update(dto);

        // Assert
        assertEquals("Bear2", result.getName());
        assertEquals(2, result.getOwnerId());
        assertEquals(2, result.getTypeId());
    }

    // ========================= DELETE =========================
    @Test
    public void testDeletePet() {

        Pet pet = TObjectCreator.newPetCreatedForDelete();

        Mockito.when(repository.findById(pet.getId()))
                .thenReturn(Optional.of(pet));

        Mockito.doNothing().when(repository).delete(pet);

        try {
            petService.delete(pet.getId());
        } catch (PetNotFoundException e) {
            fail(e.getMessage());
        }

        // ahora simulamos que ya no existe
        Mockito.when(repository.findById(pet.getId()))
                .thenReturn(Optional.empty());

        assertThrows(PetNotFoundException.class, () -> {
            petService.findById(pet.getId());
        });
    }
}