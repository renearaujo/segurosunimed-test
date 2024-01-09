package com.example.api.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "CUSTOMER")
@SequenceGenerator(name = Customer.SEQUENCE_NAME, sequenceName = Customer.SEQUENCE_NAME, allocationSize = 1)
public class Customer {

	/**
	 * Name of the sequence
	 *
	 * @see GenerationType#SEQUENCE
	 */
	public static final String SEQUENCE_NAME = "CUSTOMER_SEQ";

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_NAME)
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	@NotEmpty
	@Email(message = "Email inválido")
	private String email;

	@Column(nullable = false)
	@NotEmpty
	private String gender;

}
