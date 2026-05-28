package com.tecsup.petclinic.service;

import com.tecsup.petclinic.entities.Vet;
import com.tecsup.petclinic.exceptions.VetNotFoundException;
import com.tecsup.petclinic.repository.VetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@SpringBootTest
class VetServiceTest {

    private static final Logger log = LoggerFactory.getLogger(VetServiceTest.class);

    @Autowired
    private VetRepository vetRepository;

    @Autowired
    VetServiceImpl vetService; // ✅

    @Test
    void testFindVetById() {
        final String nameExpected = "James";
        Long id = 1L;
        Vet vet = null;

        try {
            vet = this.vetService.findVetById(id);
        } catch (RuntimeException e) {
            fail("Should not throw exception for existing vet. Error: " + e.getMessage());
        }

        assertNotNull(vet, "Vet should not be NULL");
        assertEquals(nameExpected, vet.getFirstName(), "Name should match");
    }

    @Test
    void testCreateVet() {
        Vet vetInput = new Vet();
        vetInput.setFirstName("Maria");
        vetInput.setLastName("Gomez");
        vetInput.setEmail("maria.gomez@test.com");
        vetInput.setPhone("55512345");
        vetInput.setActive(true);

        log.info("Creating Vet: {}", vetInput);

        Vet newVet = vetService.createVet(vetInput);
        log.info("New Vet created: {}", newVet);

        assertNotNull(newVet.getId(), "ID should be assigned");
        assertEquals("Maria", newVet.getFirstName(), "First name should match");
        assertEquals("Gomez", newVet.getLastName(), "Last name should match");
    }

    @Test
    void testUpdateVet() {
        String initFirstName = "Original";
        String initLastName = "Update";
        String updatedFirstName = "Updated";
        String updatedLastName = "NewName";
        String email = "update@testemail.com";
        String phone = "55554321";
        Boolean active = true;

        Vet initialVet = new Vet();
        initialVet.setFirstName(initFirstName);
        initialVet.setLastName(initLastName);
        initialVet.setEmail(email);
        initialVet.setPhone(phone);
        initialVet.setActive(active);

        log.info("Creating initial vet: {}", initialVet);
        Vet createdVet = vetService.createVet(initialVet);
        log.info("Vet created with ID: {}", createdVet.getId());

        log.info("Updating vet data");
        createdVet.setFirstName(updatedFirstName);
        createdVet.setLastName(updatedLastName);

        Vet updatedVet = vetService.updateVet(createdVet.getId(), createdVet);
        log.info("Vet updated to: {}", updatedVet);

        assertEquals(updatedFirstName, updatedVet.getFirstName(), "First name should be updated");
        assertEquals(updatedLastName, updatedVet.getLastName(), "Last name should be updated");
        assertEquals(email, updatedVet.getEmail(), "Email should remain the same");
        assertEquals(phone, updatedVet.getPhone(), "Phone should remain the same");
        assertEquals(active, updatedVet.isActive(), "Active status should remain the same");
    }

    @Test
    void testFindNonExistentVet() {
        Long nonExistentId = 999L;

        assertThrows(RuntimeException.class, () -> this.vetService.findVetById(nonExistentId),
                "Should throw RuntimeException for non-existent vet");
    }

}
