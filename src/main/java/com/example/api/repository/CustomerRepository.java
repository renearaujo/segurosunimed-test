package com.example.api.repository;

import com.example.api.domain.Customer;
import com.example.api.dto.response.CustomerSearchResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

	List<Customer> findAllByOrderByNameAsc();

	@Query(
			"SELECT new com.example.api.dto.response.CustomerSearchResponseDTO(c.id, c.name, c.email, c.gender) FROM Customer c " +
					"LEFT JOIN c.addresses a WHERE " +
					"(:email IS NULL OR TRIM(UPPER(c.email)) LIKE UPPER(CONCAT('%', TRIM(:email), '%'))) " +
					"AND " +
					"(:name IS NULL OR TRIM(UPPER(c.name)) LIKE UPPER(CONCAT('%', TRIM(:name), '%'))) " +
					"AND " +
					"(:gender IS NULL OR TRIM(UPPER(c.gender)) LIKE UPPER(CONCAT('%', TRIM(:gender), '%')))" +
					"AND " +
					"(:state IS NULL OR TRIM(UPPER(a.state)) LIKE UPPER(CONCAT('%', TRIM(:state), '%')))" +
					"AND " +
					"(:city IS NULL OR TRIM(UPPER(a.city)) LIKE UPPER(CONCAT('%', TRIM(:city), '%')))"
	)
	Page<CustomerSearchResponseDTO> findAllByFilters(
			@Param("email") @Nullable String email,
			@Param("name") @Nullable String name,
			@Param("gender") @Nullable String gender,
			@Param("state") @Nullable String state,
			@Param("city") @Nullable String city,
			Pageable pageable
	);

	Optional<Customer> findByEmailIgnoreCase(String email);

	Optional<Customer> findByEmailIgnoreCaseAndIdNot(String email, Long id);
}
