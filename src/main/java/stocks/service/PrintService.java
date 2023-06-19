package stocks.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PrintService {
    private final SymbolService symbolService;
    private final QuoteService quoteService;
    public void printSymbols() {
        symbolService.getAll().doOnNext(symbol -> log.info("symbol: " + symbol)).subscribe();
    }

    public void printStockQuotes() {
        quoteService.getTopQuotes().doOnNext(quote -> log.info("quote: " + quote)).subscribe();
    }
}
