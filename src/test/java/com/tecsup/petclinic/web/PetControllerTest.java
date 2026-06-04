package com.tecsup.petclinic.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.tecsup.petclinic.dtos.PetDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
/**
 *
 */
@AutoConfigureMockMvc
@SpringBootTest
@Slf4j
public class PetControllerTest {

    private static final ObjectMapper om = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testFindAllPets() throws Exception {

        //int NRO_RECORD = 73;
        final int ID_FIRST_RECORD = 1;

        this.mockMvc.perform(get("/pets"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                //		    .andExpect(jsonPath("$", hasSize(NRO_RECORD)))
                .andExpect(jsonPath("$[0].id", is(ID_FIRST_RECORD)));
    }


    /**
     *
     * @throws Exception
     *
     */
    @Test
    public void testFindPetOK() throws Exception {

        String PET_NAME = "Leo";
        int TYPE_ID = 1;
        int OWNER_ID = 1;
        String BIRTH_DATE = "2000-09-07";

        this.mockMvc.perform(get("/pets/1"))  // Object must be BASIL
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is(PET_NAME)))
                .andExpect(jsonPath("$.typeId", is(TYPE_ID)))
                .andExpect(jsonPath("$.ownerId", is(OWNER_ID)))
                .andExpect(jsonPath("$.birthDate", is(BIRTH_DATE)));
    }

    /**
     *
     * @throws Exception
     */
    @Test
    public void testFindPetKO() throws Exception {

        mockMvc.perform(get("/pets/666"))
                .andExpect(status().isNotFound());

    }

    /**
     * @throws Exception
     */
    @Test
    public void testCreatePet() throws Exception {

        String PET_NAME = "Beethoven";
        int TYPE_ID = 1;
        int OWNER_ID = 1;
        String BIRTH_DATE = "2020-05-20";

        PetDTO newPetTO = PetDTO.builder()
                .name(PET_NAME)
                .typeId(TYPE_ID)
                .ownerId(OWNER_ID)
                .birthDate(BIRTH_DATE)
                .build();

        this.mockMvc.perform(post("/pets")
                        .content(om.writeValueAsString(newPetTO))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                //.andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is(PET_NAME)))
                .andExpect(jsonPath("$.typeId", is(TYPE_ID)))
                .andExpect(jsonPath("$.ownerId", is(OWNER_ID)))
                .andExpect(jsonPath("$.birthDate", is(BIRTH_DATE)));

    }


    /**
     *
     * @throws Exception
     */
    @Test
    public void testDeletePet() throws Exception {

        String PET_NAME = "Beethoven3";
        int TYPE_ID = 1;
        int OWNER_ID = 1;
        String BIRTH_DATE = "2020-05-20";

        PetDTO newPetTO = PetDTO.builder()
                .name(PET_NAME)
                .typeId(TYPE_ID)
                .ownerId(OWNER_ID)
                .birthDate(BIRTH_DATE)
                .build();


        ResultActions mvcActions = mockMvc.perform(post("/pets")
                        .content(om.writeValueAsString(newPetTO))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());

        String response = mvcActions.andReturn().getResponse().getContentAsString();

        Integer id = JsonPath.parse(response).read("$.id");

        mockMvc.perform(delete("/pets/" + id ))
                /*.andDo(print())*/
                .andExpect(status().isOk());
    }

    @Test
    public void testDeletePetKO() throws Exception {

        mockMvc.perform(delete("/pets/" + "1000" ))
                /*.andDo(print())*/
                .andExpect(status().isNotFound());
    }

    /**
     * @throws Exception
     */
    @Test
    public void testUpdatePet() throws Exception {

        String PET_NAME = "Beethoven4";
        int TYPE_ID = 1;
        int OWNER_ID = 1;
        String BIRTH_DATE = "2020-05-20";

        String UP_PET_NAME = "Beethoven5";
        int UP_OWNER_ID = 2;
        int UP_TYPE_ID = 2;

        PetDTO newPetTO = PetDTO.builder()
                .name(PET_NAME)
                .typeId(TYPE_ID)
                .ownerId(OWNER_ID)
                .birthDate(BIRTH_DATE)
                .build();

        // CREATE
        ResultActions mvcActions = mockMvc.perform(post("/pets")
                        .content(om.writeValueAsString(newPetTO))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());

        String response = mvcActions.andReturn().getResponse().getContentAsString();
        Integer id = JsonPath.parse(response).read("$.id");

        // UPDATE

        PetDTO upPetTO = PetDTO.builder()
                .id(id)
                .name(UP_PET_NAME)
                .typeId(UP_TYPE_ID)
                .ownerId(UP_OWNER_ID)
                .build();

        mockMvc.perform(put("/pets/"+id)
                        .content(om.writeValueAsString(upPetTO))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        // FIND
        mockMvc.perform(get("/pets/" + id))  //
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(id)))
                .andExpect(jsonPath("$.name", is(UP_PET_NAME)))
                .andExpect(jsonPath("$.typeId", is(UP_TYPE_ID)))
                .andExpect(jsonPath("$.ownerId", is(UP_OWNER_ID)));

        // DELETE
        mockMvc.perform(delete("/pets/" + id))
                /*.andDo(print())*/
                .andExpect(status().isOk());
    }

}
