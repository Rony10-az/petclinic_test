package com.tecsup.petclinic.service;

import com.tecsup.petclinic.entities.Vet;
import com.tecsup.petclinic.exceptions.VetNotFoundException;
import com.tecsup.petclinic.repository.VetRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class VetServiceMockitoTest {

    @Mock
    private VetRepository vetRepository;

    @InjectMocks
    private VetServiceImpl vetService;

    @Test
    void testFindVetById_NotFound() {
        Long nonExistentId = 999L;
        when(vetRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        assertThrows(VetNotFoundException.class, () -> vetService.findVetById(nonExistentId));
    }

    @Test
    void testDeactivateVet_NotFound() {
        Long nonExistentId = 999L;
        when(vetRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        assertThrows(VetNotFoundException.class, () -> vetService.deactivateVet(nonExistentId));
    }

    @Test
    void testFindInactiveVets() {
        Vet inactiveVet1 = new Vet();
        inactiveVet1.setId(1L);
        inactiveVet1.setActive(false);

        Vet inactiveVet2 = new Vet();
        inactiveVet2.setId(2L);
        inactiveVet2.setActive(false);

        when(vetRepository.findByActiveFalse()).thenReturn(List.of(inactiveVet1, inactiveVet2));

        List<Vet> result = vetService.findInactiveVets();

        assertEquals(2, result.size());
        assertFalse(result.get(0).isActive());
        assertFalse(result.get(1).isActive());
    }
}