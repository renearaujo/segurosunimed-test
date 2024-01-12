package com.example.api.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Representing the customer address
 *
 * @author René Araújo Vasconcelos - 1/11/2024 - 11:57 PM
 */
@Builder
@With
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ADDRESS")
public class CustomerAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Street information from via cep API
     */
    @NotNull
    private String street;

    /**
     * City information from via cep API
     */
    @NotNull
    private String city;

    /**
     * State information from via cep API
     */
    @NotNull
    private String state;

    /**
     * Postal code information from via cep API
     */
    @NotNull
    private String postalCode;

    /**
     * Neighbourhood information from via cep API
     */
    @NotNull
    private String neighbourhood;

    /**
     * Number information from the address
     */
    @NotNull
    private String number;

    /**
     * Complement information from the address
     */
    private String complement;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

}