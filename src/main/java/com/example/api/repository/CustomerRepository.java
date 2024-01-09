package com.example.api.repository;

import com.example.api.domain.Customer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.Nullable;

import java.util.List;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

	List<Customer> findAllByOrderByNameAsc();

	@Query(
			"SELECT c FROM Customer c WHERE " +
			"(:email IS NULL OR TRIM(UPPER(c.email)) LIKE UPPER(CONCAT('%', TRIM(:email), '%'))) " +
			"AND " +
			"(:name IS NULL OR TRIM(UPPER(c.name)) LIKE UPPER(CONCAT('%', TRIM(:name), '%'))) " +
			"AND " +
			"(:gender IS NULL OR TRIM(UPPER(c.gender)) LIKE UPPER(CONCAT('%', TRIM(:gender), '%')))"
	)
	List<Customer> findAllByFilters(
			@Param("email") @Nullable String email,
			@Param("name") @Nullable String name,
			@Param("gender") @Nullable String gender
	);
}
