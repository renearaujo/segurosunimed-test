package com.example.api.repository;

import com.example.api.domain.CepInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CepInfoRepository extends JpaRepository<CepInfo, Long> {
    Optional<CepInfo> findByCepEqualsIgnoreCase(String cep);
}