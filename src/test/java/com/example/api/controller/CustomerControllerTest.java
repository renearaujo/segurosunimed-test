package com.example.api.controller;

import com.example.api.dto.CustomerDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class CustomerControllerTest {

    static final String URL = "/customers";
    static final Long ID = 1L;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreate() {

        List<CustomerDTO> customer = createCustomer(10);
        createCustomer(customer);
    }

    private void createCustomer(List<CustomerDTO> customer) {
        customer.forEach(item -> {
            try {
                mockMvc.perform(setupPost(item)).andExpect(status().isCreated())
                        .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(item.getName()))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(item.getEmail()))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.gender").value(item.getGender()));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }


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
        createCustomer(this.createCustomer(1));
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
                .andExpect(status().isOk());
    }

    @Test
    void testCreateWithoutParam() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post(URL + "/")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testCreateWithExistingEmail() throws Exception {
        CustomerDTO customer = createCustomer(1).get(0);

        String responseJson = mockMvc.perform(setupPost(customer)).andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        CustomerDTO responseDTO = objectMapper.readValue(responseJson, CustomerDTO.class);

        mockMvc.perform(setupPost(customer)).andDo(print()).andExpect(status().isConflict())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0].details")
                        .value("Email [" + customer.getEmail() + "] Already Exists for another Customer with id = [" + responseDTO.getId() + "]"));
    }

    private MockHttpServletRequestBuilder setupPost(CustomerDTO requestBody) throws JsonProcessingException {

        return MockMvcRequestBuilders
                .post(URL + "/")
                .content(objectMapper.writeValueAsString(requestBody))
                .contentType(MediaType.APPLICATION_JSON);

    }

    private List<CustomerDTO> createCustomer(int qtd) {
        List<CustomerDTO> resultList = new ArrayList<>();

        for (int i = 0; i < qtd; i++) {
            String mail = RandomStringUtils.randomAlphabetic(10) + "@gmail.com";
            resultList.add(CustomerDTO.builder().email(mail).name("joao").gender("M").build());
        }

        return resultList;
    }

    @Test
    void testCreate_InvalidName_ReturnsBadRequest() throws Exception {
        CustomerDTO customer = createCustomer(1).get(0).withName("A");

        mockMvc.perform(MockMvcRequestBuilders.post(URL + "/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0].details").value("Invalid Name: Must be of 3 - 100 characters"));
    }

}