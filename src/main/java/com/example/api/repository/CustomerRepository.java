package com.example.api.repository;

import com.example.api.domain.Customer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.Nullable;

import java.util.List;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

	List<Customer> findAllByOrderByNameAsc();

	@Query("SELECT c FROM Customer c WHERE " +
			"(:email IS NULL OR UPPER(c.email) LIKE UPPER(CONCAT('%', :email, '%')))")
	List<Customer> findByEmailContainsIgnoreCase(@Param("email") @Nullable String email);

}
