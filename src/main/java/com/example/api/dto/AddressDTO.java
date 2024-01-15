package com.example.api.dto;

import com.example.api.domain.CustomerAddress;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

/**
 * DTO for {@link CustomerAddress}
 */
@AllArgsConstructor
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddressDTO implements Serializable {
    private final Long id;
    private final String street;
    private final String city;
    private final String state;
    private final String zipCode;
    private final String neighbourhood;
    private final String number;
    private final String complement;
    private final Long customerId;
}