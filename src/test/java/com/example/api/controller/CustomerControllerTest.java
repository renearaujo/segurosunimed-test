package com.example.api.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class CustomerControllerTest {

    static final String URL = "/customers";
    static final Long ID = 1L;

    @Autowired
    MockMvc mockMvc;

    @Test
    void testFindAllShouldOK() throws Exception
    {
        mockMvc.perform(MockMvcRequestBuilders
                        .get(URL)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testFindByIdShouldOK() throws Exception
    {
        mockMvc.perform( MockMvcRequestBuilders
                        .get(URL+"/{id}", ID)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testFindByIdShouldBadRequest() throws Exception
    {
        mockMvc.perform( MockMvcRequestBuilders
                        .get(URL+"/{id}", "IDL")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testFindByIdShouldNotFound() throws Exception
    {
        mockMvc.perform( MockMvcRequestBuilders
                        .get(URL+"/{id}", 444444444)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testFindByFilterShouldOK() throws Exception
    {
        mockMvc.perform( MockMvcRequestBuilders
                        .get(URL+"/filter")
                        .param("email", ".com")
                        .param("name", "joao")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testFindByFilterShouldOKWithoutParams() throws Exception
    {
        mockMvc.perform( MockMvcRequestBuilders
                        .get(URL+"/filter")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testFindByFilterShouldOKWithBlankParam() throws Exception
    {
        mockMvc.perform( MockMvcRequestBuilders
                        .get(URL+"/filter")
                        .param("email", "")
                        .param("name", "")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
}