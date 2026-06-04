package com.tecsup.petclinic.service;

import com.tecsup.petclinic.entities.Vet;
import com.tecsup.petclinic.repository.VetRepository;
import org.junit.jupiter.api.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class VetServiceTest {

    private static final Logger log = LoggerFactory.getLogger(VetServiceTest.class);

    @Autowired
    private VetRepository vetRepository;

    @Autowired
    VetServiceImpl vetService;

    @Test
    void testFindVetById() {
        Vet vet = vetService.findVetById(1L);
        assertNotNull(vet);
        assertEquals("James", vet.getFirstName());
    }

    @Test
    void testCreateVet() {
        Vet vet = new Vet();
        vet.setFirstName("Maria");
        vet.setLastName("Gomez");
        vet.setEmail("maria@test.com");
        vet.setPhone("123");
        vet.setActive(true);

        Vet created = vetService.createVet(vet);

        assertNotNull(created.getId());
        assertEquals("Maria", created.getFirstName());
    }

    @Test
    void testUpdateVet() {
        Vet vet = new Vet();
        vet.setFirstName("A");
        vet.setLastName("B");
        vet.setEmail("a@test.com");
        vet.setPhone("111");
        vet.setActive(true);

        Vet created = vetService.createVet(vet);

        created.setFirstName("Updated");

        Vet updated = vetService.updateVet(created.getId(), created);

        assertEquals("Updated", updated.getFirstName());
    }

    @Test
    void testFindNonExistentVet() {
        assertThrows(RuntimeException.class,
                () -> vetService.findVetById(999L));
    }

    @Test
    void testDeactivateVet() {
        Vet vet = new Vet();
        vet.setFirstName("Deactivate");
        vet.setLastName("Vet");
        vet.setEmail("deactivate@test.com");
        vet.setPhone("123");
        vet.setActive(true);

        Vet created = vetService.createVet(vet);

        // ✔ método oficial (void)
        vetService.deactivateVet(created.getId());

        Vet result = vetService.findVetById(created.getId());

        assertFalse(result.isActive());
    }

    @Test
    void testReactivateVet() {
        Vet vet = new Vet();
        vet.setFirstName("Reactivate");
        vet.setLastName("Vet");
        vet.setEmail("reactivate@test.com");
        vet.setPhone("456");
        vet.setActive(false);

        Vet created = vetService.createVet(vet);

        // ✔ método extra (de otra rama)
        Vet reactivated = vetService.reactivateVet(created.getId());

        assertTrue(reactivated.isActive());
    }

    @Test
    void testFindActiveVets() {
        Vet active = new Vet();
        active.setFirstName("Active");
        active.setLastName("Vet");
        active.setEmail("active@test.com");
        active.setPhone("111");
        active.setActive(true);

        Vet inactive = new Vet();
        inactive.setFirstName("Inactive");
        inactive.setLastName("Vet");
        inactive.setEmail("inactive@test.com");
        inactive.setPhone("222");
        inactive.setActive(false);

        Vet savedActive = vetService.createVet(active);
        vetService.createVet(inactive);

        List<Vet> result = vetService.findActiveVets();

        assertTrue(result.stream().allMatch(Vet::isActive));
        assertTrue(result.stream().anyMatch(v -> v.getId().equals(savedActive.getId())));
    }
}