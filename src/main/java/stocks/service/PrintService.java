package stocks.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import stocks.dto.StockQuoteDto;
import stocks.dto.SymbolDto;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PrintService {
    private final StockService stockService;
    public void printSymbols() {
        System.out.println(stockService.getAvailableSymbols());
    }

    public void printStockQuotes() {
        List<SymbolDto> symbols = stockService.getAvailableSymbols();
        List<StockQuoteDto> quotes = stockService.getQuotes(symbols);
        for (StockQuoteDto quote : quotes) {
            System.out.println(quote);
        }
    }
}
