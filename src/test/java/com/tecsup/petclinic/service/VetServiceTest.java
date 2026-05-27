package com.tecsup.petclinic.service;

import com.tecsup.petclinic.entities.Vet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
class VetServiceTest {

    private static final Logger log = LoggerFactory.getLogger(VetServiceTest.class);

    @Autowired
    private VetService vetService;

    @BeforeEach
    void setUp() {
    }

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

    @Test
    void testDeactivateVet() {
        Vet vetInput = new Vet();
        vetInput.setFirstName("Deactivate");
        vetInput.setLastName("Vet");
        vetInput.setEmail("deactivate.vet@test.com");
        vetInput.setPhone("55500001");
        vetInput.setActive(true);

        Vet createdVet = vetService.createVet(vetInput);
        Vet deactivatedVet = vetService.deactivateVet(createdVet.getId());
        Vet persistedVet = vetService.findVetById(createdVet.getId());

        assertFalse(deactivatedVet.isActive(), "Vet should be inactive after deactivation");
        assertEquals(createdVet.getId(), persistedVet.getId(), "Vet record should still exist");
        assertFalse(persistedVet.isActive(), "Inactive status should be persisted");
    }

    @Test
    void testReactivateVet() {
        Vet vetInput = new Vet();
        vetInput.setFirstName("Reactivate");
        vetInput.setLastName("Vet");
        vetInput.setEmail("reactivate.vet@test.com");
        vetInput.setPhone("55500002");
        vetInput.setActive(false);

        Vet createdVet = vetService.createVet(vetInput);
        Vet reactivatedVet = vetService.reactivateVet(createdVet.getId());
        Vet persistedVet = vetService.findVetById(createdVet.getId());

        assertTrue(reactivatedVet.isActive(), "Vet should be active after reactivation");
        assertTrue(persistedVet.isActive(), "Active status should be persisted");
    }

    @Test
    void testFindActiveVets() {
        Vet activeVetInput = new Vet();
        activeVetInput.setFirstName("Active");
        activeVetInput.setLastName("Vet");
        activeVetInput.setEmail("active.vet@test.com");
        activeVetInput.setPhone("55500003");
        activeVetInput.setActive(true);

        Vet inactiveVetInput = new Vet();
        inactiveVetInput.setFirstName("Inactive");
        inactiveVetInput.setLastName("Vet");
        inactiveVetInput.setEmail("inactive.vet@test.com");
        inactiveVetInput.setPhone("55500004");
        inactiveVetInput.setActive(false);

        Vet activeVet = vetService.createVet(activeVetInput);
        Vet inactiveVet = vetService.createVet(inactiveVetInput);

        List<Vet> activeVets = vetService.findActiveVets();

        assertTrue(activeVets.stream().allMatch(Vet::isActive), "All returned vets should be active");
        assertTrue(activeVets.stream().anyMatch(vet -> vet.getId().equals(activeVet.getId())),
                "Created active vet should be returned");
        assertFalse(activeVets.stream().anyMatch(vet -> vet.getId().equals(inactiveVet.getId())),
                "Created inactive vet should not be returned");
    }
}
