package com.example.api.dto.response;

import com.example.api.domain.CustomerAddress;
import com.example.api.dto.CustomerDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

/**
 * DTO for {@link CustomerAddress}
 */
@Getter
@Setter
@AllArgsConstructor
@Builder
@With
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerAddressResponseDto {
    private final Long id;
    private final String street;
    private final String city;
    private final String state;
    private final String zipCode;
    private final String neighbourhood;
    private final String number;
    private final String complement;
    private final CustomerDTO customer;
}