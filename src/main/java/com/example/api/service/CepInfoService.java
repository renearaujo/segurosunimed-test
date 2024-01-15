package com.example.api.service;

import com.example.api.domain.CepInfo;
import com.example.api.repository.CepInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class CepInfoService {

    private final CepInfoRepository repository;

    public Optional<CepInfo> findByCep(String cep) {
        return this.repository.findByCepEqualsIgnoreCase(cep);
    }

}
