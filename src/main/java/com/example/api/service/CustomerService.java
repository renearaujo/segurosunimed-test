package com.example.api.service;

import com.example.api.domain.Customer;
import com.example.api.exception.CustomerNotFoundException;
import com.example.api.exception.EmailAlreadyExistsException;
import com.example.api.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
     * @param id id to be searched
     * @return the {@link Customer} founded
     * @throws CustomerNotFoundException exception if a customer was not found
     * @author René Araújo Vasconcelos - 1/8/2024 - 2:34 PM
     */
    public Customer findById(Long id) throws CustomerNotFoundException {
        return repository.findById(id).orElseThrow(() -> new CustomerNotFoundException(id));
    }

    /**
     * Find a {@link Customer} by filters
     *
     * @param email  email to be used to filter customers
     * @param name   name to be used to filter customers
     * @param gender gender to be used to filter customers
     * @return a {@link List} of {@link Customer}s
     * @author René Araújo Vasconcelos - 1/8/2024 - 7:55 PM
     */
    public List<Customer> findAllByFilters(String email, String name, String gender) {
        return repository.findAllByFilters(email, name, gender);
    }

    @Transactional
    public Customer create(Customer item) {

        Optional<Customer> customerResultDB = repository.findByEmailIgnoreCase(item.getEmail());

        if (customerResultDB.isPresent()) {
            throw new EmailAlreadyExistsException(customerResultDB.get().getEmail(), customerResultDB.get().getId());
        }

        item.setId(null);
        Customer saved = repository.save(item);

        log.info("Customer created with id = {} - email = {} ", saved.getId(), saved.getEmail());

        return saved;
    }

    @Transactional
    public void delete(Long id) {
        Customer customer = this.repository.findById(id).orElseThrow(() -> new CustomerNotFoundException(id));
        this.repository.deleteById(customer.getId());
        log.info("Customer with id = [{}] successfully deleted", id);
    }
}
