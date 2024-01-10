package com.example.api.service;

import com.example.api.domain.Customer;
import com.example.api.exception.CustomerNotFoundException;
import com.example.api.exception.EmailAlreadyExistsException;
import com.example.api.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
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
	 * @author René Araújo Vasconcelos - 1/8/2024 - 7:55 PM
	 * @param email email to be used to filter
	 * @param name name to be used to find
	 * @return a {@link List} of {@link Customer}s
	 */
	public List<Customer> findAllByFilters(String email, String name) {
		return repository.findAllByFilters(email, name);
	}

	public Customer create(Customer item) {

		Optional<Customer> customerResultDB = repository.findByEmailIgnoreCase(item.getEmail());

		if (customerResultDB.isPresent()) {
			throw new EmailAlreadyExistsException(customerResultDB.get().getEmail(), customerResultDB.get().getId());
		}

		Customer saved = repository.save(item);

		log.info("Customer created with id = {} - email = {} ", saved.getId(), saved.getEmail());

		return saved;
	}
}
