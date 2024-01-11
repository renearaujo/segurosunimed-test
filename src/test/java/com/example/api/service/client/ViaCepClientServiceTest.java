package com.example.api.service.client;

import com.example.api.dto.ViaCepApiResponse;
import com.example.api.exception.CepNotFoundException;
import com.example.api.exception.MalformedCepException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.MessageFormat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
@SpringBootTest
class ViaCepClientServiceTest {

    @Mock
    private ObjectMapper objectMapper;
    private ViaCepClientService service;

    @BeforeEach
    void setUp() {
        this.service = new ViaCepClientService(objectMapper);
    }

    @Test
    void testFindByCep() throws IOException, InterruptedException {
        // Arrange
        String cep = "60160080";
        String responseBody = "{\n" +
                "  \"cep\": \"60160-080\",\n" +
                "  \"logradouro\": \"Rua Tomás Pompeu\",\n" +
                "  \"complemento\": \"\",\n" +
                "  \"bairro\": \"Meireles\",\n" +
                "  \"localidade\": \"Fortaleza\",\n" +
                "  \"uf\": \"CE\",\n" +
                "  \"ibge\": \"2304400\",\n" +
                "  \"gia\": \"\",\n" +
                "  \"ddd\": \"85\",\n" +
                "  \"siafi\": \"1389\"\n" +
                "}";

        HttpClient mockHttpClient = Mockito.mock(HttpClient.class);
        HttpResponse mockHttpResponse = Mockito.mock(HttpResponse.class);
        Mockito.when(mockHttpResponse.body()).thenReturn(responseBody);

        Mockito.when(mockHttpClient.send(any(HttpRequest.class), any())).thenReturn(mockHttpResponse);

        Mockito.when(objectMapper.readValue(responseBody, ViaCepApiResponse.class))
                .thenReturn(ViaCepApiResponse.builder()
                        .cep("60160-080")
                        .logradouro("Rua Tomás Pompeu")
                        .complemento("")
                        .bairro("Meireles")
                        .localidade("Fortaleza")
                        .uf("CE")
                        .ibge("2304400")
                        .gia("")
                        .ddd("85")
                        .siafi("1389")
                        .build());

        // Act
        ViaCepApiResponse result = service.findByCep(cep);

        // Assert
        assertEquals("60160-080", result.getCep());
        assertEquals("Rua Tomás Pompeu", result.getLogradouro());
        assertEquals("", result.getComplemento());
        assertEquals("Meireles", result.getBairro());
        assertEquals("Fortaleza", result.getLocalidade());
        assertEquals("CE", result.getUf());
        assertEquals("2304400", result.getIbge());
        assertEquals("", result.getGia());
        assertEquals("85", result.getDdd());
        assertEquals("1389", result.getSiafi());
    }

    @Test
    void testFindByCepWithInvalidCepLessThan8() {
        this.testInvalid(RandomStringUtils.randomNumeric(6));
    }

    @Test
    void testFindByCepWithInvalidCepMoreThan8() {
        this.testInvalid(RandomStringUtils.randomNumeric(10));
    }

    @Test
    void testFindByCepWithInvalidCepAlphanumeric() {
        this.testInvalid(RandomStringUtils.randomAlphanumeric(8));
    }

    private void testInvalid(String invalidCep) {
        assertEquals(MessageFormat.format(MalformedCepException.MSG_TEMPLATE, invalidCep), assertThrows(MalformedCepException.class, () -> service.findByCep(invalidCep)).getLocalizedMessage());
    }

    @Test
    void testFindByCepNotFound() throws IOException, InterruptedException {
        // Arrange
        String cep = "60160999";
        String responseBody = "{\n" +
                "  \"erro\": true\n" +
                "}";

        HttpClient mockHttpClient = Mockito.mock(HttpClient.class);
        HttpResponse mockHttpResponse = Mockito.mock(HttpResponse.class);
        Mockito.when(mockHttpResponse.body()).thenReturn(responseBody);

        Mockito.when(mockHttpClient.send(any(HttpRequest.class), any())).thenReturn(mockHttpResponse);

        Mockito.when(objectMapper.readValue(responseBody, ViaCepApiResponse.class))
                .thenReturn(ViaCepApiResponse.builder()
                        .erro(true)
                        .build());

        assertEquals(MessageFormat.format(CepNotFoundException.MSG_TEMPLATE, cep), assertThrows(CepNotFoundException.class, () -> service.findByCep(cep)).getMessage());
    }

}
