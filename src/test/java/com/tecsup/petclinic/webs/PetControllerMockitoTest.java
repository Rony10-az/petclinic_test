package com.tecsup.petclinic.webs;

import com.tecsup.petclinic.entities.Vet;
import com.tecsup.petclinic.service.VetService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(VetController.class)
public class PetControllerMockitoTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private VetService vetService;

    @Test
    public void testFindAllVets() throws Exception {
        Vet vet = new Vet(1L, "James", "Carter", "james.carter@petclinic.com", "6085551234", true);
        when(vetService.findAllVets()).thenReturn(List.of(vet));

        mockMvc.perform(get("/vets"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].lastName").value("Carter"));
    }

    @Test
    public void testFindVetOK() throws Exception {
        Vet vet = new Vet(1L, "James", "Carter", "james.carter@petclinic.com", "6085551234", true);
        when(vetService.findVetById(1L)).thenReturn(vet);

        mockMvc.perform(get("/vets/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lastName").value("Carter"));
    }

    @Test
    public void testFindVetKO() throws Exception {
        when(vetService.findVetById(666L)).thenThrow(new RuntimeException("Vet not found with id: 666"));

        mockMvc.perform(get("/vets/666"))
                .andExpect(status().isNotFound());
    }
}
