package com.example.demo.service;

import com.example.demo.client.IexApiClient;
import com.example.demo.model.StockEntity;
import com.example.demo.model.CompanyEntity;
import com.example.demo.repository.CompanyRepository;
import com.example.demo.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompanyService {

    private final IexApiClient iexApiClient;
    private final StockRepository stockRepository;
    private final CompanyRepository companyRepository;

    @Transactional
    public void processCompanyDetails() {
        List<CompanyEntity> companyEntities = Arrays.stream(iexApiClient.getAllSymbols()).toList();
        companyRepository.saveAll(companyEntities);

        List<CompletableFuture<StockEntity>> completableFutures = companyEntities
                .parallelStream().map(iexApiClient::getCompanyData)
                //TODO: for testing limit 50
                .limit(50)
                .toList();

        List<StockEntity> stockEntities = completableFutures
                .parallelStream()
                .map(CompletableFuture::join)
                .toList();

        List<StockEntity> listWithoutNulls = stockEntities.parallelStream()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        stockRepository.saveAll(listWithoutNulls);
    }
}
