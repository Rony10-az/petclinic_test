package com.tecsup.petclinic.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("h2")
class VetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // =========================
    // Integrante A — Listado y consulta
    // =========================

    @Test
    void testFindAllVets() throws Exception {
        mockMvc.perform(get("/vets"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()", greaterThanOrEqualTo(6)));
    }

    @Test
    void testFindVetOK() throws Exception {
        mockMvc.perform(get("/vets/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lastName").value("Carter"));
    }

    @Test
    void testFindVetKO() throws Exception {
        mockMvc.perform(get("/vets/666"))
                .andExpect(status().isNotFound());
    }

    // =========================
    // Integrante B — Creación y soft delete
    // =========================

    @Test
    void testCreateVet() throws Exception {
        String body = """
                {
                  "firstName": "Ana",
                  "lastName": "Lopez",
                  "email": "ana.lopez.integration@test.com",
                  "phone": "55510001",
                  "active": true
                }
                """;

        mockMvc.perform(post("/vets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.firstName").value("Ana"))
                .andExpect(jsonPath("$.active").value(true));
    }

    @Test
    void testDeactivateVet() throws Exception {
        mockMvc.perform(put("/vets/2/deactivate"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.active").value(false));
    }

    @Test
    void testReactivateVet() throws Exception {
        mockMvc.perform(put("/vets/6/reactivate"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(6))
                .andExpect(jsonPath("$.firstName").value("Sharon"))
                .andExpect(jsonPath("$.active").value(true));
    }

    // =========================
    // Integrante C — Filtros y borrado
    // =========================

    @Test
    void testFindActiveVets() throws Exception {
        mockMvc.perform(get("/vets?active=true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[*].active").value(
                        org.hamcrest.Matchers.everyItem(org.hamcrest.Matchers.is(true))));
    }

    @Test
    void testFindInactiveVets() throws Exception {
        mockMvc.perform(get("/vets?active=false"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[*].active").value(
                        org.hamcrest.Matchers.everyItem(org.hamcrest.Matchers.is(false))));
    }

    @Test
    void testDeleteVetKO() throws Exception {
        mockMvc.perform(delete("/vets/1000"))
                .andExpect(status().isNotFound());
    }
}
