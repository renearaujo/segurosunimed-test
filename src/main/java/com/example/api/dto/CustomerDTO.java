package com.example.api.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

/**
 * DTO that used for a Customer
 *
 * @author René Araújo Vasconcelos - 1/8/2024 - 2:27 PM
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerDTO {

    @NotNull(message="O nome não pode ser nulo")
    private String name;

    @NotNull(message="O email não pode ser nulo")
    private String email;

    @NotNull(message="O genero não pode ser nulo")
    private String gender;

}
