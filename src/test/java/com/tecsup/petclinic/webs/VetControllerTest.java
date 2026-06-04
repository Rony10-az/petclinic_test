package com.tecsup.petclinic.webs;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class VetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testCreateVet() throws Exception {
        String requestBody = """
                {
                  "firstName": "Ana",
                  "lastName": "Lopez",
                  "email": "ana.lopez@test.com",
                  "phone": "55510001",
                  "active": true
                }
                """;

        mockMvc.perform(post("/vets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.firstName").value("Ana"))
                .andExpect(jsonPath("$.lastName").value("Lopez"))
                .andExpect(jsonPath("$.email").value("ana.lopez@test.com"))
                .andExpect(jsonPath("$.phone").value("55510001"))
                .andExpect(jsonPath("$.active").value(true));
    }

    @Test
    void testDeactivateVet() throws Exception {
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
