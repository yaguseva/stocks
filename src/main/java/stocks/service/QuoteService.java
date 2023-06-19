package stocks.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import stocks.client.IexApiClient;
import stocks.dto.QuoteDto;
import stocks.entity.Quote;
import stocks.entity.Symbol;
import stocks.repository.QuoteRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuoteService {
    private final IexApiClient iexApiClient;
    private final QuoteRepository quoteRepository;

    public Flux<Quote> collectData(Flux<Symbol> symbolFlux) {
        Flux<Quote> quotes = symbolFlux.flatMap(symbol -> iexApiClient.getQuoteBySymbol(symbol.getSymbol()))
                .map(QuoteDto::toEntity);
        return quoteRepository.saveAll(quotes);
    }

    public Flux<QuoteDto> getTopQuotes() {
        return quoteRepository.findTopStocks().map(Quote::toDto);
    }
}
