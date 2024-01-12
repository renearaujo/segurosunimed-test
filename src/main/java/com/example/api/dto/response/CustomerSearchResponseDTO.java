package com.example.api.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.*;

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
@With
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonRootName("customer")
public class CustomerSearchResponseDTO {

    private String name;
    private String email;
    private String gender;
    private Long id;

    public CustomerSearchResponseDTO(Long id, String name, String email, String gender) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.gender = gender;
    }
}
