package stocks.service;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import stocks.dto.StockQuoteDto;
import stocks.dto.SymbolDto;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StockService {
    private final WebClient webClient;

    public List<SymbolDto> getAvailableSymbols() {
        return webClient.get()
                .uri(builder -> builder.path("/data/core/ref_data_iex_symbols")
                        .queryParam("token", "pk_f78754823d9243de88aa16c6de5e249c")
                        .build())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .exchangeToMono(response -> response.bodyToMono(new ParameterizedTypeReference<List<SymbolDto>>() {}))
                .block();
    }

    public Optional<StockQuoteDto> getStockQuote(String symbol) {
        List<StockQuoteDto> quotes = webClient.get()
                .uri(builder -> builder.path("/data/core/quote/" + symbol)
                        .queryParam("token", "pk_f78754823d9243de88aa16c6de5e249c")
                        .build())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .exchangeToMono(response -> response.bodyToMono(new ParameterizedTypeReference<List<StockQuoteDto>>() {
                }))
                .block();
        if (quotes == null || quotes.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(quotes.get(0));
    }

    public Queue<SymbolDto> createQueue(List<SymbolDto> symbols) {
        return symbols.stream()
                .filter(SymbolDto::getIsEnabled)
                .collect(Collectors.toCollection(LinkedList::new));
    }
}
