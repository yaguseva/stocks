package stocks.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrintService {
    private final StockService stockService;
    public void printSymbols() {
        System.out.println(stockService.getAvailableSymbols());
    }
}
