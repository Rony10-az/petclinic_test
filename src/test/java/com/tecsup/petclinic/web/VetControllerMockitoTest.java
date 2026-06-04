package com.tecsup.petclinic.web;

import com.tecsup.petclinic.dtos.VetDTO;
import com.tecsup.petclinic.exceptions.VetNotFoundException;
import com.tecsup.petclinic.service.VetService;
import com.tecsup.petclinic.mappers.VetMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("h2")
class VetControllerMockitoTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private VetService vetService;

    @MockitoBean
    private VetMapper mapper;

    @Test
    void testFindActiveVets() throws Exception {

        VetDTO dto = new VetDTO(1L, "Juan", "Perez", "a@test.com", "111", true);

        when(vetService.findActiveVets()).thenReturn(List.of());
        when(mapper.toDTO(any())).thenReturn(dto);

        mockMvc.perform(get("/vets?active=true"))
                .andExpect(status().isOk());
    }

    @Test
    void testFindInactiveVets() throws Exception {

        VetDTO dto = new VetDTO(2L, "Carlos", "Lopez", "b@test.com", "222", false);

        when(vetService.findInactiveVets()).thenReturn(List.of());
        when(mapper.toDTO(any())).thenReturn(dto);

        mockMvc.perform(get("/vets?active=false"))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteVetKO() throws Exception {

        doThrow(new VetNotFoundException("Vet not found"))
                .when(vetService).deleteVet(1000L);

        mockMvc.perform(delete("/vets/1000"))
                .andExpect(status().isNotFound());
    }
}