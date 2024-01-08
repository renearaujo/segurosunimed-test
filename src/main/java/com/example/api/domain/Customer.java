package com.example.api.domain;

import com.example.api.dto.CustomerDTO;
import lombok.*;
import org.modelmapper.ModelMapper;

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
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	@NotEmpty
	@Email
	private String email;

	@Column(nullable = false)
	@NotEmpty
	private String gender;

	/**
	 * Converts the {@link Customer} entity to DTO
	 *
	 * @author René Araújo Vasconcelos - 1/8/2024 - 2:28 PM
	 *
	 * @return the DTO {@link CustomerDTO}
	 */
	public CustomerDTO convertToDTO() {
		return new ModelMapper().map(this, CustomerDTO.class);
	}

}
