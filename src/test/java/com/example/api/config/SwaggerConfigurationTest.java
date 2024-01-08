package com.example.api.config;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class SwaggerConfigurationTest {

    public static final String DEFAULT_URL = "/swagger-ui/index.html";
    public static final String CUSTOM_URL = "/swagger";
    public static final String PAGE_TITLE = "Swagger UI";

    @Autowired
    protected MockMvc mockMvc;

    @Test
    void shouldDisplaySwaggerUiPage() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get(DEFAULT_URL)).andExpect(status().isOk()).andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        Assertions.assertTrue(contentAsString.contains(PAGE_TITLE));
    }

    @Test
    void shouldRedirectToSwaggerUiPage() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get(CUSTOM_URL)).andExpect(status().is3xxRedirection()).andReturn();
        String contentAsString = mvcResult.getResponse().getRedirectedUrl();
        assert contentAsString != null;
        Assertions.assertTrue(contentAsString.contains(DEFAULT_URL));
    }

}