package stocks.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import stocks.dto.QuoteDto;
import stocks.dto.SymbolDto;
import stocks.entity.Quote;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PrintService {
    private final SymbolService symbolService;
    private final QuoteService quoteService;
    public void printSymbols() {
        System.out.println(symbolService.getAvailableSymbols());
    }

    public void printStockQuotes() {
        List<SymbolDto> symbols = symbolService.getAvailableSymbols();
        List<QuoteDto> quotes = quoteService.getQuotes(symbols);
        for (QuoteDto quote : quotes) {
            System.out.println(quote);
        }
        List<Quote> topQuotes = quoteService.getTopQuotes();
        System.out.println("The top 5 highest value stocks: ");
        for (Quote topQuote : topQuotes) {
            System.out.println(topQuote.getCompanyName() + " volume: " + topQuote.getVolume());
        }
    }
}
