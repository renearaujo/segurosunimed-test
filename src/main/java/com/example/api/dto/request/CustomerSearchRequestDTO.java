package com.example.api.dto.request;

import lombok.*;
import org.springframework.data.domain.Pageable;

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
public class CustomerSearchRequestDTO {

    private String name;
    private String email;
    private String gender;
    private String state;
    private String city;
    Pageable pageable;
}
