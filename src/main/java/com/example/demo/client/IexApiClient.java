package com.example.demo.client;

import com.example.demo.model.StockEntity;
import com.example.demo.model.CompanyEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;

@Component
@Slf4j
public class IexApiClient {
    @Value("${service.token}")
    private String TOKEN;
    @Value("${service.all-symbols-url}")
    private String ALL_SYMBOLS_URL;

    @Value("${service.company-data}")
    private String COMPANY_URL;


    public CompletableFuture<StockEntity> getCompanyData(CompanyEntity companyEntity) {
        String urlWithSymbol = String.format(COMPANY_URL, companyEntity.getSymbol());
        RestTemplate restTemplate = new RestTemplate();
        StockEntity stockEntity = null;

        log.info(companyEntity.toString());
        try {
            ResponseEntity<StockEntity> response =
                    restTemplate.getForEntity(
                            urlWithSymbol + TOKEN,
                            StockEntity.class);
            stockEntity = response.getBody();
        } catch (Exception e) {
            log.error("Exception for symbol: " + companyEntity + ": " + e);
        }
        return CompletableFuture.completedFuture(stockEntity);
    }

    public CompanyEntity[] getAllSymbols() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<CompanyEntity[]> response =
                restTemplate.getForEntity(
                        ALL_SYMBOLS_URL + TOKEN,
                        CompanyEntity[].class);
        return response.getBody();
    }
}
