package com.example.api.dto;

import com.example.api.domain.Customer;
import lombok.*;
import org.modelmapper.ModelMapper;

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

    /**
     * Converts the {@link CustomerDTO} DTO to entity
     *
     * @author René Araújo Vasconcelos - 1/8/2024 - 2:29 PM
     *
     * @return the entity {@link Customer}
     */
    public Customer convertToEntity() {
        return new ModelMapper().map(this, Customer.class);
    }

}
