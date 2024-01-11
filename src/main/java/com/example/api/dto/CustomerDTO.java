package com.example.api.dto;

import com.example.api.validation.OnCreate;
import com.example.api.validation.OnUpdate;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.*;

import javax.validation.constraints.*;

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
public class CustomerDTO {

    @NotBlank(message = "Invalid Name: Empty name")
    @NotNull(message = "Invalid Name: Name is NULL")
    @Size(min = 3, max = 100, message = "Invalid Name: Must be of 3 - 100 characters")
    private String name;

    @NotBlank(message = "Invalid Name: Empty email")
    @NotNull(message = "Invalid Name: email is NULL")
    @Email(message = "Invalid email")
    private String email;

    @NotBlank(message = "Invalid Name: Empty Gender")
    @NotNull(message = "Invalid Name: gender is NULL")
    private String gender;

    @Null(groups = OnCreate.class)
    @NotNull(groups = OnUpdate.class)
    private Long id;
}
