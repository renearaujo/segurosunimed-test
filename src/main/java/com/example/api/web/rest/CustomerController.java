package com.example.api.web.rest;

import com.example.api.domain.Customer;
import com.example.api.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/customers")
@Tag(name = "CUSTOMER", description = "Endpoint especifico para operações do customer")
public class CustomerController {

	private CustomerService service;

	@Autowired
	public CustomerController(CustomerService service) {
		this.service = service;
	}

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
					@Content(mediaType = "application/json", schema = @Schema(implementation = Customer.class))}),
			@ApiResponse(responseCode = "400", description = "id inválido", content = @Content),
			@ApiResponse(responseCode = "404", description = "customer não encontrado", content = @Content) })
	@GetMapping("/{id}")
	public Customer findById(@Parameter(description = "id para buscar um customer especifico") @PathVariable Long id) {
		return service.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));
	}

}
