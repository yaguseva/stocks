package com.example.demo.job;

import com.example.demo.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProcessDataJob {

    private final CompanyService companyService;

    @Scheduled(fixedRateString = "${job.process.data.rate}")
    public void test() {
        companyService.processCompanyDetails();
    }

}
