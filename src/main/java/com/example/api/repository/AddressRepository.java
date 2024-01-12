package com.example.api.repository;

import com.example.api.domain.CustomerAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<CustomerAddress, Long> {

    @Query("select c from CustomerAddress c " +
            "where upper(c.postalCode) = upper(:postalCode) and upper(c.number) = upper(:number) and upper(c.complement) = upper(:complement) and c.customer.id = :customerId")
    Optional<CustomerAddress> findAddressAlreadyExists(@Param("postalCode") String postalCode, @Param("number") String number, @Param("complement") String complement, @Param("customerId") Long customerId);

}