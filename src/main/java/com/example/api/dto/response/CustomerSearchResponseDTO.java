package com.example.api.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.*;

/**
 * DTO used for projection and response from customer search
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

    /**
     * name of the customer
     */
    private String name;

    /**
     * email of the customer
     */
    private String email;

    /**
     * gender of the customer
     */
    private String gender;

    /**
     * id of the customer
     */
    private Long id;

    /**
     * constructor used as projection
     *
     * @author René Araújo Vasconcelos - 1/12/2024 - 11:32 AM
     * @param id id of the customer
     * @param name name of the customer
     * @param email email of the customer
     * @param gender gender of the customer
     */
    public CustomerSearchResponseDTO(Long id, String name, String email, String gender) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.gender = gender;
    }
}
