package com.example.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.With;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * DTO for {@link com.example.api.domain.CustomerAddress}
 */
@AllArgsConstructor
@Getter
@Setter
@With
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerAddressDto implements Serializable {
    public static final String REGEX_ONLY_2_LETTERS = "[A-Z]{2}";

    private final Long id;
    @NotNull
    @NotEmpty
    @NotBlank
    private final String street;
    @NotNull
    @NotEmpty
    @NotBlank
    private final String city;
    @NotNull
    @NotEmpty
    @NotBlank
    private final String state;
    @NotNull
    @NotEmpty
    @NotBlank
    private final String zipCode;
    @NotNull
    @NotEmpty
    @NotBlank
    private final String neighbourhood;
}