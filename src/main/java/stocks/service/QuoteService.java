package stocks.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import stocks.client.IexApiClient;
import stocks.dto.QuoteDto;
import stocks.entity.Quote;
import stocks.entity.Symbol;
import stocks.repository.QuoteRepository;
import stocks.repository.SymbolRepository;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuoteService {
    private final IexApiClient iexApiClient;
    private final QuoteRepository quoteRepository;

    @Transactional
    public void collectData(Flux<Symbol> symbolFlux) {
        Flux<Quote> quotes = symbolFlux.flatMap(symbol ->
                        iexApiClient.getQuoteBySymbol(symbol.getSymbol()))
                .map(QuoteDto::toEntity);
        quoteRepository.saveAll(quotes);
    }

    public List<Quote> getTopQuotes() {
        return quoteRepository.findTopStocks().collectList().blockOptional().orElse(Collections.emptyList());
    }
}
