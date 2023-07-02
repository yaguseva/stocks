package com.example.demo.client;

import com.example.demo.AppTests;
import com.example.demo.model.StockEntity;
import com.example.demo.model.CompanyEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CompletableFuture;

@RunWith(SpringRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class IexApiClientTest extends AppTests {

    @Autowired
    private IexApiClient client;

    @Test
    void getCompanyData() {
        CompanyEntity companyEntity = CompanyEntity.builder()
                .symbol("DOYU")
                .build();
        CompletableFuture<StockEntity> completableFuture = client.getCompanyData(companyEntity);
        Assertions.assertEquals(companyEntity.getSymbol(), completableFuture.join().getSymbol());
    }

    @Test
    void getAllSymbols() {
        CompanyEntity[] employees =  client.getAllSymbols();
        Assertions.assertTrue(employees.length > 0);
    }

}
