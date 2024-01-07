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

    public static final String INDEX_HTML = "/swagger-ui/index.html";
    public static final String URL_BASE = "/swagger";
    public static final String PAGE_TITLE = "Swagger UI";

    @Autowired
    protected MockMvc mockMvc;

    @Test
    void shouldDisplaySwaggerUiPage() throws Exception {
        mockMvc.perform(get(URL_BASE)).andExpect(status().is3xxRedirection());
        MvcResult mvcResult = mockMvc.perform(get(INDEX_HTML)).andExpect(status().isOk()).andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        Assertions.assertTrue(contentAsString.contains(PAGE_TITLE));
    }

}