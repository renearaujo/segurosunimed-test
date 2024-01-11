package com.example.api.service.client;

import com.example.api.dto.ViaCepApiResponse;
import com.example.api.exception.CepNotFoundException;
import com.example.api.exception.MalformedCepException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import static java.time.temporal.ChronoUnit.MINUTES;

/**
 * Service to access information from via cep
 *
 * @author René Araújo Vasconcelos - 1/11/2024 - 10:17 AM
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ViaCepClientService {

    /**
     * Base URL to access via cep service
     */
    private static final String BASE_URL = "https://viacep.com.br/ws/";

    /**
     * Suffix to access information from via cep service
     */
    private static final String SUFFIX = "/json";

    /**
     * Regex representing the correct format of the cep
     */
    public static final String REGEX_ONLY_8_DIGITS = "\\d{8}";

    private final ObjectMapper objectMapper;

    public ViaCepApiResponse findByCep(@NotNull final String cep) throws IOException, InterruptedException {
        log.info("validating the cep [{}] informed", cep);
        validateCep(cep);
        HttpClient httpClient = setupHttpClient();
        HttpRequest httpRequest = createViaCepRequest(cep);
        log.info("requesting via cep information for cep: [{}]", cep);
        HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        log.info("receiving via cep information for the cep [{}]: {}]", cep, httpResponse.body());
        ViaCepApiResponse viaCepApiResponse = objectMapper.readValue(httpResponse.body(), ViaCepApiResponse.class);
        validateViaCepResponse(cep, viaCepApiResponse);

        return viaCepApiResponse;
    }

    /**
     * Validate via cep response
     *
     * @param cep               cep searched
     * @param viaCepApiResponse response from the via cep api
     * @author René Araújo Vasconcelos - 1/11/2024 - 3:21 PM
     */
    private static void validateViaCepResponse(String cep, ViaCepApiResponse viaCepApiResponse) {
        if (viaCepApiResponse.isErro()) {
            log.warn("Cep [{}] wasn't found on ViaCep API", cep);
            throw new CepNotFoundException(cep);
        }
    }

    /**
     * Validates If the cep does not meet the criteria, doesn't have 8 digits, the exception {@link MalformedCepException} is thrown
     *
     * @param cep cep to be validated
     * @author René Araújo Vasconcelos - 1/11/2024 - 1:18 PM
     */
    private void validateCep(String cep) {

        if (!cep.matches(REGEX_ONLY_8_DIGITS)) {
            log.warn("cep [{}] is malformed!", cep);
            throw new MalformedCepException(cep);
        }
        log.info("cep [{}] is valid!", cep);
    }

    /**
     * Setup and define Http Client configurations: timeout, retries
     *
     * @author René Araújo Vasconcelos - 1/11/2024 - 10:43 AM
     */
    private HttpClient setupHttpClient() {
        int timeoutInMinutes = 1;
        log.info("setup via cep connection with timeout: [{} minute(s)]", timeoutInMinutes);
        return HttpClient.newBuilder()
                .connectTimeout(Duration.of(timeoutInMinutes, MINUTES))
                .build();
    }

    /**
     * Create request
     *
     * @param cep to be user to define uri
     * @return request with uri configured
     * @author René Araújo Vasconcelos - 1/11/2024 - 5:04 PM
     * @see ViaCepClientService#BASE_URL
     * @see ViaCepClientService#SUFFIX
     */
    private HttpRequest createViaCepRequest(final String cep) {

        final String uri = BASE_URL + cep + SUFFIX;
        log.info("creating via cep request with URI: [{}]", uri);
        return HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(uri))
                .build();
    }
}