package stocks.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import stocks.entity.Symbol;

@Component
@RequiredArgsConstructor
public class DataProcessingService {

    private final SymbolService symbolService;
    private final QuoteService quoteService;

    public void process() {
        Flux<Symbol> symbolFlux = symbolService.process();
        quoteService.collectData(symbolFlux);
    }
}
