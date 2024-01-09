package com.example.api.service;

import com.example.api.domain.Customer;
import com.example.api.exception.CustomerNotFoundException;
import com.example.api.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CustomerService {

	private final CustomerRepository repository;

	public List<Customer> findAll() {
		return repository.findAllByOrderByNameAsc();
	}

	/**
	 * Returns a {@link Customer}
	 *
	 * @author René Araújo Vasconcelos - 1/8/2024 - 2:34 PM
	 * @param id id to be searched
	 * @return the {@link Customer} founded
	 *
	 * @throws CustomerNotFoundException exception if a customer was not found
	 */
	public Customer findById(Long id) throws CustomerNotFoundException {
		return repository.findById(id).orElseThrow(() -> new CustomerNotFoundException("Cliente com id=" + id +" não encontrado"));
	}

	/**
	 * Find a {@link Customer} by filters
	 *
	 * @param email  email to be used to filter customers
	 * @param name   name to be used to filter customers
	 * @param gender gender to be used to filter customers
	 * @return a {@link List} of {@link Customer}s
	 *
	 * @author René Araújo Vasconcelos - 1/8/2024 - 7:55 PM
	 */
	public List<Customer> findAllByFilters(String email, String name, String gender) {
		return repository.findAllByFilters(email, name, gender);
	}

}
