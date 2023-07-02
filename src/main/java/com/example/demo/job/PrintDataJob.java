package com.example.demo.job;

import com.example.demo.model.StockEntity;
import com.example.demo.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PrintDataJob {

    @Autowired
    private StockRepository repo;

    @Scheduled(fixedRateString = "${job.print.by.price}")
    public void buildTop5ByPrice() {
        List<StockEntity> list = repo.findTop5ByLatestPrice();
        list.forEach(System.out::println);
    }

    @Scheduled(fixedRateString = "${job.print.by.percent.change}")
    public void buildTop5ByPercentChange() {
        List<StockEntity> list = repo.findTop5ByChangePercent();
        list.forEach(System.out::println);
    }
}
