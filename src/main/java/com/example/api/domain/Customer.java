package com.example.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "CUSTOMER", indexes = {
		@Index(name = Customer.INDEX, columnList = "email")
})
public class Customer {

	public static final String INDEX = "idx_customer_email_unq";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false, unique = true)
	@NotEmpty
	@Email(message = "Email inválido")
	private String email;

	@Column(nullable = false)
	@NotEmpty
	private String gender;

	@JsonIgnore
	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<CustomerAddress> addresses = new ArrayList<>();

}
