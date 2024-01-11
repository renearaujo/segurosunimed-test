package com.example.api.controller;

import com.example.api.dto.CustomerDTO;
import com.example.api.exception.CustomerNotFoundException;
import com.example.api.exception.EmailAlreadyExistsException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

class CustomerControllerTest {

    static final String URL = "/customers";
    static final Long ID = 1L;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Order(1)
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
    void testFindAllByFilters() throws Exception {

        int totalElements = 10;

        List<CustomerDTO> customer = createCustomer(totalElements);
        createCustomer(customer);

        mockMvc.perform(MockMvcRequestBuilders
                        .get(URL + "/filters")
                        .param("email", customer.get(1).getName())
                        .param("name", "test")
                        .param("gender", "m")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sortBy", "name")
                        .param("sortOrder", "ASC")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalElements").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalElementsThisPage").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.pageNumber").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.pageIndex").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalPages").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isFirst").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isLast").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.hasNext").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.hasPrevious").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isArray());
    }

    @Test
    void testFindByFilterShouldOKWithBlankParam() throws Exception
    {
        mockMvc.perform(MockMvcRequestBuilders
                        .get(URL + "/filters")
                        .param("email", "")
                        .param("name", "any()")
                        .param("gender", "")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sortBy", "name")
                        .param("sortOrder", "ASC")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalElements").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalElementsThisPage").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.pageNumber").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.pageIndex").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalPages").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isFirst").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isLast").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.hasNext").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.hasPrevious").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isArray());
    }

    @Test
    void testCreateWithoutParam() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post(URL + "/")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void testCreateWithExistingEmail() throws Exception {
        CustomerDTO customer = createCustomer(1).get(0);

        String responseJson = mockMvc.perform(setupPost(customer)).andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        CustomerDTO responseDTO = objectMapper.readValue(responseJson, CustomerDTO.class);

        mockMvc.perform(setupPost(customer)).andExpect(status().isConflict())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0].details")
                        .value(MessageFormat.format(EmailAlreadyExistsException.MSG_TEMPLATE, customer.getEmail(), responseDTO.getId())));
    }

    private CustomerDTO createAndGetResponse() throws Exception {
        CustomerDTO customer = createCustomer(1).get(0);

        String responseJson = mockMvc.perform(setupPost(customer)).andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        return objectMapper.readValue(responseJson, CustomerDTO.class);
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

    @Test
    void testDelete_shouldThrowExceptionWhenCustomerNotFound() throws Exception {
        Long id = 2222222L;

        mockMvc.perform(MockMvcRequestBuilders
                        .delete(URL + "/{id}", id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0].details").value(MessageFormat.format(CustomerNotFoundException.MSG_TEMPLATE, id)));
    }

    @Test
    void testDelete_shouldDelete() throws Exception {

        CustomerDTO customerDTO = this.createAndGetResponse();

        mockMvc.perform(MockMvcRequestBuilders
                        .delete(URL + "/{id}", customerDTO.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        mockMvc.perform(MockMvcRequestBuilders
                        .get(URL + "/{id}", customerDTO.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0].details").value(MessageFormat.format(CustomerNotFoundException.MSG_TEMPLATE, customerDTO.getId())));

    }

    @Test
    @Order(2)
    void testUpdateCustomer() throws Exception {
        String newName = RandomStringUtils.randomAlphabetic(1, 33);
        CustomerDTO customer = createAndGetResponse().withName(newName);
        String requestBody = objectMapper.writeValueAsString(customer);

        // Perform the request and validate the response
        mockMvc.perform(MockMvcRequestBuilders
                        .put(URL + "/{id}", customer.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(newName))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(customer.getEmail()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.gender").value(customer.getGender()));
    }

    @Test
    void testUpdateCustomerSameData() throws Exception {
        CustomerDTO customer = createAndGetResponse();

        // When
        mockMvc.perform(MockMvcRequestBuilders.put(URL + "/{id}", customer.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(customer.getName()));

    }


    @Test
    void testUpdateCustomerInvalidData() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put(URL + "/{id}", ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new CustomerDTO())))
                .andExpect(status().isBadRequest());

    }

    @Test
    void testUpdateCustomerNotFound() throws Exception {

        String newName = RandomStringUtils.randomAlphabetic(4, 33);
        CustomerDTO customer = createAndGetResponse().withName(newName);

        Long customerId = 11111L;

        mockMvc.perform(MockMvcRequestBuilders.put(URL + "/{id}", customerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isNotFound());

    }

    @Test
    void testUpdateCustomerEmailAlreadyExistsForAnotherCustomer() throws Exception {

        CustomerDTO customer = createAndGetResponse();
        CustomerDTO customer2 = createAndGetResponse();

        mockMvc.perform(MockMvcRequestBuilders.put(URL + "/{id}", customer2.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customer2.withEmail(customer.getEmail()))))
                .andExpect(status().isConflict());

    }

}