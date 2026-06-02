package com.tecsup.petclinic.service;

import com.tecsup.petclinic.entities.Vet;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
class VetServiceTest {

    private static final Logger log = LoggerFactory.getLogger(VetServiceTest.class);

    @Autowired
    private VetService vetService;

    @Test
    void testFindVetById() {
        final String firstNameExpected = "James";
        final String lastNameExpected = "Carter";
        Long id = 1L;
        Vet vet = null;

        try {
            vet = this.vetService.findVetById(id);
        } catch (RuntimeException e) {
            fail("Should not throw exception for existing vet. Error: " + e.getMessage());
        }

        assertNotNull(vet, "Vet should not be NULL");
        assertEquals(firstNameExpected, vet.getFirstName(), "First name should match seed data");
        assertEquals(lastNameExpected, vet.getLastName(), "Last name should match seed data");
    }

    @Test
    void testCreateVet() {
        Vet vetInput = new Vet();
        vetInput.setFirstName("Maria");
        vetInput.setLastName("Gomez");
        vetInput.setEmail("maria.gomez.integration@test.com");
        vetInput.setPhone("55512345");
        vetInput.setActive(true);

        log.info("Creating Vet: {}", vetInput);

        Vet newVet = vetService.createVet(vetInput);
        log.info("New Vet created: {}", newVet);

        assertNotNull(newVet.getId(), "ID should be assigned");

        Vet persistedVet = vetService.findVetById(newVet.getId());

        assertEquals("Maria", persistedVet.getFirstName(), "First name should be persisted in H2");
        assertEquals("Gomez", persistedVet.getLastName(), "Last name should be persisted in H2");
        assertEquals("maria.gomez.integration@test.com", persistedVet.getEmail(), "Email should be persisted in H2");
        assertEquals("55512345", persistedVet.getPhone(), "Phone should be persisted in H2");
        assertEquals(true, persistedVet.getActive(), "Active flag should be persisted in H2");
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

        Vet persistedVet = vetService.findVetById(updatedVet.getId());

        assertEquals(updatedFirstName, persistedVet.getFirstName(), "First name should be updated in H2");
        assertEquals(updatedLastName, persistedVet.getLastName(), "Last name should be updated in H2");
        assertEquals(email, persistedVet.getEmail(), "Email should remain the same");
        assertEquals(phone, persistedVet.getPhone(), "Phone should remain the same");
        assertEquals(active, persistedVet.getActive(), "Active status should remain the same");
    }
}
