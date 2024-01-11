package com.example.api.service;

import com.example.api.domain.Customer;
import com.example.api.dto.CustomerDTO;
import com.example.api.exception.CustomerNotFoundException;
import com.example.api.exception.EmailAlreadyExistsException;
import com.example.api.repository.CustomerRepository;
import com.example.api.util.MapperUtils;
import com.example.api.validation.OnCreate;
import com.example.api.validation.OnUpdate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
@Validated
public class CustomerService {

    private final CustomerRepository repository;
    private final MapperUtils mapperUtils;

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
    public CustomerDTO findById(Long id) throws CustomerNotFoundException {
        return mapperUtils.map(repository.findById(id).orElseThrow(() -> new CustomerNotFoundException(id)), CustomerDTO.class);
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
    public List<CustomerDTO> findAllByFilters(String email, String name, String gender) {
        return mapperUtils.mapAll(repository.findAllByFilters(email, name, gender), CustomerDTO.class);
    }

    @Transactional
    @Validated(OnCreate.class)
    public CustomerDTO create(@Valid CustomerDTO item) {

        repository.findByEmailIgnoreCase(item.getEmail())
                .ifPresent(i -> {
                    throw new EmailAlreadyExistsException(item.getEmail(), i.getId());
                });

        Customer saved = this.repository.save(mapperUtils.map(item, Customer.class));

        log.info("Customer created with id = [{}],  email = [{}] ", saved.getId(), saved.getEmail());
        mapperUtils.merge(saved, item);
        return item;
    }

    @Transactional
    public void delete(Long id) {
        Customer customer = this.repository.findById(id).orElseThrow(() -> new CustomerNotFoundException(id));
        this.repository.deleteById(customer.getId());
        log.info("Customer with id = [{}] successfully deleted", id);
    }

    @Transactional
    @Validated(OnUpdate.class)
    public CustomerDTO update(Long id, CustomerDTO dto) {

        Customer existingCustomer = repository.findById(id).orElseThrow(() -> new CustomerNotFoundException(id));

        repository.findByEmailIgnoreCaseAndIdNot(dto.getEmail(), id)
                .ifPresent(i -> {
                    throw new EmailAlreadyExistsException(dto.getEmail(), i.getId());
                });

        dto.setId(id);
        mapperUtils.merge(dto, existingCustomer);
        Customer saved = repository.save(existingCustomer);

        log.info("Customer updated with id = [{}], email = [{}] ", saved.getId(), saved.getEmail());

        return mapperUtils.map(saved, CustomerDTO.class);
    }

}
