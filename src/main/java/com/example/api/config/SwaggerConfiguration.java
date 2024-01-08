package com.example.api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Swagger configuration class
 *  
 * @author René Araújo Vasconcelos - 1/8/2024 - 11:05 AM
 */
@Configuration
@Profile({"dev"})
public class SwaggerConfiguration {

	public static final String API_TITLE = "SEGUROS UNIMED JAVA API";
	public static final String API_DESCRIPTION = "API de teste da Seguros UNIMED - Documentação dos Endpoints";
	public static final String CONCAT_RELEASE_API_VERSION = "_";

	@Value("${release.version}")
	private String releaseVersion;
	
	@Value("${api.version}")
	private String apiVersion;

	@Bean
	public OpenAPI openAPI() {

		final String version = releaseVersion.concat(CONCAT_RELEASE_API_VERSION).concat(apiVersion);

		return new OpenAPI()
				.info(new Info().title(API_TITLE)
						.description(API_DESCRIPTION)
						.version(version));
	}

}