package com.example.api.service;

import com.example.api.domain.Customer;
import com.example.api.domain.CustomerAddress;
import com.example.api.dto.CepApiResponse;
import com.example.api.dto.request.CreateCustomerAddressRequestDTO;
import com.example.api.exception.CustomerAddressAlreadyExistsException;
import com.example.api.repository.AddressRepository;
import com.example.api.service.client.CepClientService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@Service
public class AddressService {

    private final AddressRepository repository;
    private final CepClientService viaCepClientService;
    private final CustomerService customerService;

    @SneakyThrows
    @Transactional
    public CustomerAddress create(@Valid CreateCustomerAddressRequestDTO dto, Long customerId) {
        // get customer and validate if exists
        Customer customer = this.customerService.getById(customerId);
        // get address and validate if cep exists
        CepApiResponse viaCepApiResponse = this.viaCepClientService.findByCep(dto.getCep());
        // validate address
        this.validateAddress(customer.getId(), dto);
        // setup new address with all the information
        CustomerAddress newAddress = setupNewAddress(dto, customer, viaCepApiResponse);
        // save data
        CustomerAddress customerAddressDB = this.repository.save(newAddress);
        log.info("Address [id={}] for the Customer [id={}] successfully created", customerAddressDB.getId(), customerAddressDB.getCustomer().getId());
        return customerAddressDB;
    }

    private CustomerAddress setupNewAddress(CreateCustomerAddressRequestDTO dto, Customer customer, CepApiResponse viaCepApiResponse) {
        return CustomerAddress.builder()
                .customer(customer)
                .zipCode(dto.getCep())
                .city(viaCepApiResponse.getLocalidade())
                .state(viaCepApiResponse.getUf())
                .complement(dto.getComplement())
                .number(dto.getNumber())
                .neighbourhood(viaCepApiResponse.getBairro())
                .street(viaCepApiResponse.getLogradouro())
                .build();
    }

    private void validateAddress(Long customerId, CreateCustomerAddressRequestDTO dto) {
        this.repository.findAddressAlreadyExists(dto.getCep(), dto.getNumber(), dto.getComplement(), customerId)
                .ifPresent(i -> {
                    throw new CustomerAddressAlreadyExistsException(dto.getComplement(), dto.getNumber(), dto.getCep(), i.getId(), i.getCustomer().getId());
                });
    }

}
