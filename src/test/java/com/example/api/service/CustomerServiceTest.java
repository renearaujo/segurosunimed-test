package com.example.api.service;


import com.example.api.domain.Customer;
import com.example.api.dto.CustomerDTO;
import com.example.api.exception.EmailAlreadyExistsException;
import com.example.api.repository.CustomerRepository;
import com.example.api.util.MapperUtils;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
class CustomerServiceTest {

    @InjectMocks
    private CustomerService service;

    @Mock
    private CustomerRepository repository;

    @Mock
    private MapperUtils mapperUtils;

    @Mock
    private Validator validator;

    @Test
    void testCreateNewCustomerWithValidData() {

        CustomerDTO dto = createTestCustomerDTO();
        Customer entity = createTestCustomerEntity();

        when(repository.findByEmailIgnoreCase(dto.getEmail())).thenReturn(Optional.empty());
        when(mapperUtils.map(dto, Customer.class)).thenReturn(entity);
        when(repository.save(any())).thenReturn(entity);
        when(mapperUtils.map(entity, CustomerDTO.class)).thenReturn(dto);
        when(validator.supports(CustomerDTO.class)).thenReturn(true);

        Errors errors = new BeanPropertyBindingResult(dto, "customerDTO");
        validator.validate(dto, errors);

        CustomerDTO createdCustomerDTO = service.create(dto);

        assertNotNull(createdCustomerDTO);
        assertFalse(errors.hasErrors());
    }

    @Test
    void testCreateCustomerWithExistingEmail() {
        CustomerDTO dto = createTestCustomerDTO();
        when(repository.findByEmailIgnoreCase(dto.getEmail()))
                .thenReturn(Optional.of(createTestCustomerEntity()));

        assertThrows(EmailAlreadyExistsException.class, () -> service.create(dto));
    }

    private CustomerDTO createTestCustomerDTO() {
        return CustomerDTO.builder().email("test@gmail.com").name("test").gender("M").build();
    }

    private Customer createTestCustomerEntity() {
        return Customer.builder().id(1L).name("teste").gender("F").email("teste@gmail.com").build();
    }

}
