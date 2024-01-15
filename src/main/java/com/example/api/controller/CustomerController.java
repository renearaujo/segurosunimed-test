package com.example.api.controller;

import com.example.api.domain.CustomerAddress;
import com.example.api.dto.CustomerDTO;
import com.example.api.dto.request.CreateCustomerAddressRequestDTO;
import com.example.api.dto.request.CustomerSearchRequestDTO;
import com.example.api.dto.response.CustomerSearchResponseDTO;
import com.example.api.dto.response.UnimedPagedResponse;
import com.example.api.exception.CustomerNotFoundException;
import com.example.api.service.AddressService;
import com.example.api.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Controller for Customer operations/Routes
 *
 * @author René Araújo Vasconcelos - 1/12/2024 - 10:43 AM
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/customers")
@Tag(name = "CUSTOMER", description = "Endpoint for u")
@Validated
public class CustomerController {

	private final CustomerService service;
	private final AddressService addressService;

	@Operation(summary = "Find all", description = "Find all the customers")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "List of all customers", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = CustomerSearchResponseDTO.class))}),
			})
	@GetMapping
	public ResponseEntity<List<CustomerSearchResponseDTO>> findAll() {
		return ResponseEntity.ok().body(this.service.findAllCustom());
	}

	@Operation(summary = "Rota para buscar um customer por id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Customer Found", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = CustomerDTO.class))}),
			@ApiResponse(responseCode = "400", description = "id inválido", content = @Content),
			@ApiResponse(responseCode = "404", description = "customer não encontrado", content = @Content) })
	@GetMapping("/{id}")
	public CustomerDTO findById(@Parameter(description = "id para buscar um customer especifico") @PathVariable Long id) throws CustomerNotFoundException {
		return service.findById(id);
	}

	@GetMapping("/filters")
	public ResponseEntity<UnimedPagedResponse<CustomerSearchResponseDTO>> findAllByFilters(
			@Parameter(description = "email para consulta") @RequestParam(required = false) String email,
			@Parameter(description = "Nome para consulta") @RequestParam(required = false) String name,
			@Parameter(description = "Genero para consulta") @RequestParam(required = false) String gender,
			@Parameter(description = "Estado do cliente para consulta") @RequestParam(required = false) String state,
			@Parameter(description = "cidade do cliente para consulta") @RequestParam(required = false) String city,
			@Parameter(description = "Número da página (começando em 0)", example = "0") @RequestParam(defaultValue = "0") int page,
			@Parameter(description = "Tamanho da página", example = "10") @RequestParam(defaultValue = "10") int size,
			@Parameter(description = "Atributo para ordenação. Exemplo: name") @RequestParam(required = false, defaultValue = "name") String sortBy,
			@Parameter(description = "Ordem de ordenação. Exemplo: asc ou desc") @RequestParam(defaultValue = "ASC") Sort.Direction sortOrder
	) {
		CustomerSearchRequestDTO requestDTO = CustomerSearchRequestDTO.builder()
				.state(state)
				.email(email)
				.city(city)
				.gender(gender)
				.name(name)
				.pageable(PageRequest.of(page, size, Sort.by(sortOrder, sortBy)))
				.build();
		return ResponseEntity.ok().body(this.service.findAllByFilters(requestDTO));
	}
	@Operation(
			summary = "Create Customer REST API",
			description = "Create Customer REST API is used to save customer in a database"
	)
	@ApiResponse(
			responseCode = "201",
			description = "HTTP Status 201 CREATED"
	)
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CustomerDTO create(@Valid @RequestBody CustomerDTO request) {
		return service.create(request);
	}

	@Operation(
			summary = "delete Customer REST API",
			description = "delete Customer REST API is used to delete customer in a database"
	)
	@DeleteMapping(value = "/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) throws CustomerNotFoundException {
		this.service.delete(id);
	}

	@PutMapping(value = "/{id}")
	public CustomerDTO update(@Valid @RequestBody CustomerDTO request, @PathVariable Long id) {
		return this.service.update(id, request);
	}

	@PostMapping(value = "/{id}/addresses")
	@ResponseStatus(HttpStatus.CREATED)
	public CustomerAddress create(@Valid @RequestBody CreateCustomerAddressRequestDTO request, @PathVariable Long id) {
		return this.addressService.create(request, id);
	}

}
