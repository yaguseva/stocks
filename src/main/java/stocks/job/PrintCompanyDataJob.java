package stocks.job;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import stocks.service.PrintService;

@Component
@RequiredArgsConstructor
public class PrintCompanyDataJob {
    private final PrintService printService;

    @Scheduled(cron="${data.printing.cron}")
    public void print() {
        printService.printSymbols();
        printService.printStockQuotes();
    }
}
