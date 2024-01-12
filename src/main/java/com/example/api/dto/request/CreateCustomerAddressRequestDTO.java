package com.example.api.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import static com.example.api.service.client.ViaCepClientService.REGEX_ONLY_8_DIGITS;

/**
 * Request DTO used for create a new customer address
 *
 * @author René Araújo Vasconcelos - 1/8/2024 - 2:27 PM
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@With
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonRootName("address")
public class CreateCustomerAddressRequestDTO {

    @NotBlank(message = "Invalid CEP: Empty")
    @NotNull(message = "Invalid CEP: Null")
    @Pattern(regexp = REGEX_ONLY_8_DIGITS, message = "Invalid CEP: Only numbers and Must be 8 digits")
    private String cep;

    @NotBlank(message = "Invalid complement: Empty")
    @NotNull(message = "Invalid complement: Null")
    private String complement;

    @NotBlank(message = "Invalid number: Empty")
    @NotNull(message = "Invalid number: Null")
    private String number;

}
