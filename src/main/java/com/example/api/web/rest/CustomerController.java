package com.example.api.web.rest;

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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/customers")
@Tag(name = "CUSTOMER", description = "Endpoint especifico para operações do customer")
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
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<SeguroUnimedResponse<CustomerDTO>> findById(@Parameter(description = "id para buscar um customer especifico") @PathVariable Long id) throws CustomerNotFoundException {

		SeguroUnimedResponse<CustomerDTO> response = new SeguroUnimedResponse<>();
		Customer customer = service.findById(id);

		CustomerDTO dto = customer.convertToDTO();
		response.setData(dto);

		return ResponseEntity.ok().body(response);
	}
}
