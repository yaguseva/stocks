package stocks.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import stocks.dto.SymbolDto;

import java.util.List;
import java.util.Queue;

@Service
@RequiredArgsConstructor
public class PrintService {
    private final StockService stockService;
    public void printSymbols() {
        System.out.println(stockService.getAvailableSymbols());
    }

    public void printStockQuotes() {
        List<SymbolDto> symbols = stockService.getAvailableSymbols();
        Queue<SymbolDto> queue = stockService.createQueue(symbols);
        for (SymbolDto symbol : queue) {
            System.out.println(stockService.getStockQuote(symbol.getSymbol()));
        }
    }
}
