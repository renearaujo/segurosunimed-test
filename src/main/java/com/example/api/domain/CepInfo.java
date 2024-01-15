package com.example.api.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Cep basic information
 *
 * @author René Araújo Vasconcelos - 1/14/2024 - 12:36 PM
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@With
@Entity
@Table(name = "CEP_INFO", indexes = {
        @Index(name = CepInfo.INDEX, columnList = "cep")
})
public class CepInfo {

    public static final String INDEX = "idx_cep_info_cep_unq";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @NotNull
    @Column(unique = true, length = 8)
    private String cep;

    @NotNull
    @Column(length = 400)
    private String logradouro;

    @NotNull
    @Column
    private String localidade;

    @NotNull
    @Column(length = 2)
    private String uf;

    @NotNull
    private String bairro;
}