package com.example.api.controller;

import com.example.api.domain.Customer;
import com.example.api.dto.CustomerDTO;
import com.example.api.dto.response.SeguroUnimedResponse;
import com.example.api.exception.CustomerNotFoundException;
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
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/customers")
@Tag(name = "CUSTOMER", description = "Endpoint especifico para operações do customer")
@Validated
public class CustomerController {

	private final CustomerService service;

	@Operation(summary = "Rota para buscar todos os customers")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "lista de customers", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation =Customer.class))}),
			})
	@GetMapping
	public List<Customer> findAll() {
		return service.findAll();
	}

	@Operation(summary = "Rota para buscar um customer por id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "customer encontrado", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = CustomerDTO.class))}),
			@ApiResponse(responseCode = "400", description = "id inválido", content = @Content),
			@ApiResponse(responseCode = "404", description = "customer não encontrado", content = @Content) })
	@GetMapping("/{id}")
	public CustomerDTO findById(@Parameter(description = "id para buscar um customer especifico") @PathVariable Long id) throws CustomerNotFoundException {
		return service.findById(id);
	}

	@Operation(summary = "Rota para buscar todos os customers filtrados por email")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "lista de customers", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation =SeguroUnimedResponse.class))}),
	})

	@GetMapping("/filter")
	public List<CustomerDTO> findAllByFilters(
			@Parameter(description = "email para consulta") @RequestParam(required = false) String email,
			@Parameter(description = "Nome para consulta") @RequestParam(required = false) String name,
			@Parameter(description = "Genero para consulta") @RequestParam(required = false) String gender
	) {
		return service.findAllByFilters(email, name, gender);
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

}
