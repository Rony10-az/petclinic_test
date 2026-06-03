package com.tecsup.petclinic.webs;

import com.tecsup.petclinic.entities.Vet;
import com.tecsup.petclinic.service.VetService;
import com.tecsup.petclinic.util.TObjectCreator;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class VetControllerMockitoTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private VetService vetService;

    @Test
    void testCreateVet() throws Exception {
        Vet newVetCreated = TObjectCreator.newVetCreated();
        String requestBody = """
                {
                  "firstName": "Ana",
                  "lastName": "Lopez",
                  "email": "ana.lopez@test.com",
                  "phone": "55510001",
                  "active": true
                }
                """;

        Mockito.when(this.vetService.createVet(Mockito.any(Vet.class)))
                .thenReturn(newVetCreated);

        mockMvc.perform(post("/vets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/vets/7"))
                .andExpect(jsonPath("$.id").value(7))
                .andExpect(jsonPath("$.firstName").value("Ana"))
                .andExpect(jsonPath("$.lastName").value("Lopez"))
                .andExpect(jsonPath("$.email").value("ana.lopez@test.com"))
                .andExpect(jsonPath("$.phone").value("55510001"))
                .andExpect(jsonPath("$.active").value(true));
    }

    @Test
    void testDeactivateVet() throws Exception {
        Vet deactivatedVet = TObjectCreator.deactivatedVet();

        Mockito.when(this.vetService.deactivateVet(1L))
                .thenReturn(deactivatedVet);

        mockMvc.perform(put("/vets/{id}/deactivate", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstName").value("James"))
                .andExpect(jsonPath("$.lastName").value("Carter"))
                .andExpect(jsonPath("$.email").value("james.carter@petclinic.com"))
                .andExpect(jsonPath("$.phone").value("6085551234"))
                .andExpect(jsonPath("$.active").value(false));
    }

    @Test
    void testReactivateVet() throws Exception {
        Vet reactivatedVet = TObjectCreator.reactivatedVet();

        Mockito.when(this.vetService.reactivateVet(6L))
                .thenReturn(reactivatedVet);

        mockMvc.perform(put("/vets/{id}/reactivate", 6L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(6))
                .andExpect(jsonPath("$.firstName").value("Sharon"))
                .andExpect(jsonPath("$.lastName").value("Jenkins"))
                .andExpect(jsonPath("$.email").value("sharon.jenkins@petclinic.com"))
                .andExpect(jsonPath("$.phone").value("6085556789"))
                .andExpect(jsonPath("$.active").value(true));
    }
}
