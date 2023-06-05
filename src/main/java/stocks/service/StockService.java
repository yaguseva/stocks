package stocks.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;
import reactor.core.publisher.Mono;
import stocks.dto.StockQuoteDto;
import stocks.dto.SymbolDto;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

@Slf4j
@Service
@RequiredArgsConstructor
public class StockService {
    private final WebClient webClient;

    public List<SymbolDto> getAvailableSymbols() {
        return webClient.get()
                .uri(getUri("/data/core/ref_data_iex_symbols"))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .exchangeToMono(response -> response.bodyToMono(new ParameterizedTypeReference<List<SymbolDto>>() {}))
                .block();
    }

    public Optional<StockQuoteDto> getStockQuote(String symbol) {
        log.info("Request quote for " + symbol);
        Optional<List<StockQuoteDto>> quotes = webClient.get()
                .uri("/data/core/quote/" + symbol)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .exchangeToMono(response -> {
                    if (HttpStatus.OK.equals(response.statusCode())) {
                        return response.bodyToMono(new ParameterizedTypeReference<List<StockQuoteDto>>() {
                        });
                    } else if (response.statusCode().is4xxClientError()) {
                        log.info("Request failed with code " + response.statusCode());
                        return Mono.empty();
                    } else {
                        return response.createException().flatMap(Mono::error);
                    }
                }).blockOptional();
        if (quotes.isPresent() && !quotes.get().isEmpty()) {
            return Optional.of(quotes.get().get(0));
        }
        return Optional.empty();
    }

    public List<StockQuoteDto> getQuotes(List<SymbolDto> symbols) {
        List<Supplier<Optional<StockQuoteDto>>> tasks = new ArrayList<>();
//        todo: remove
//        for (int i = 0; i < 50; i++) {
//            SymbolDto symbol = symbols.get(i);
//            if (symbol.getIsEnabled()) {
//                tasks.add(() -> getStockQuote(symbol.getSymbol()));
//            }
//        }

        for (SymbolDto symbol : symbols) {
            if (symbol.getIsEnabled()) {
                tasks.add(() -> getStockQuote(symbol.getSymbol()));
            }
        }

        List<CompletableFuture<Optional<StockQuoteDto>>> completableFutures = new ArrayList<>(tasks.size());
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        for (Supplier<Optional<StockQuoteDto>> task : tasks) {
            CompletableFuture<Optional<StockQuoteDto>> future = new CompletableFuture<>();
            completableFutures.add(future.completeAsync(task, executorService));
        }

        List<StockQuoteDto> result = new ArrayList<>();
        for (CompletableFuture<Optional<StockQuoteDto>> completableFuture : completableFutures) {
            try {
                completableFuture.get().ifPresent(result::add);
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }
        executorService.shutdown();
        return result;
    }

    private static URI getUri(String path) {
        return new DefaultUriBuilderFactory().builder().path(path)
                .queryParam("token", "pk_f78754823d9243de88aa16c6de5e249c")
                .build();
    }
}
