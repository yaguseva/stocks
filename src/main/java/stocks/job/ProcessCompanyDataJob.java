package stocks.job;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import stocks.service.DataProcessingService;

@Component
@RequiredArgsConstructor
public class ProcessCompanyDataJob {

    private final DataProcessingService dataProcessingService;

    @Scheduled(cron="${data.processing.cron}")
    public void process() {
        dataProcessingService.process();
    }
}
