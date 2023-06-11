package stocks.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import stocks.dto.QuoteDto;
import stocks.dto.SymbolDto;
import stocks.entity.Quote;
import stocks.repository.QuoteRepository;

import java.util.ArrayList;
import java.util.Collections;
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
public class QuoteService {
    private final WebClient webClient;
    private final QuoteRepository quoteRepository;

    public Optional<QuoteDto> getStockQuote(String symbol) {
        log.info("Request quote for " + symbol);
        Optional<List<QuoteDto>> quotes = webClient.get()
                .uri(builder -> builder.path("/data/core/quote/" + symbol)
                        .queryParam("token", "pk_d209595a36ab41c9a69d4672f428038c")
                        .build())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .exchangeToMono(response -> {
                    if (HttpStatus.OK.equals(response.statusCode())) {
                        return response.bodyToMono(new ParameterizedTypeReference<List<QuoteDto>>() {
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

    public List<QuoteDto> getQuotes(List<SymbolDto> symbols) {
        List<Supplier<Optional<QuoteDto>>> tasks = new ArrayList<>();
//        todo: remove
        for (int i = 0; i < 5; i++) {
            SymbolDto symbol = symbols.get(i);
            if (symbol.getIsEnabled()) {
                tasks.add(() -> getStockQuote(symbol.getSymbol()));
            }
        }

//        for (SymbolDto symbol : symbols) {
//            if (symbol.getIsEnabled()) {
//                tasks.add(() -> getStockQuote(symbol.getSymbol()));
//            }
//        }

        List<CompletableFuture<Optional<QuoteDto>>> completableFutures = new ArrayList<>(tasks.size());
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        for (Supplier<Optional<QuoteDto>> task : tasks) {
            CompletableFuture<Optional<QuoteDto>> future = new CompletableFuture<>();
            completableFutures.add(future.completeAsync(task, executorService));
        }

        List<QuoteDto> result = new ArrayList<>();
        for (CompletableFuture<Optional<QuoteDto>> completableFuture : completableFutures) {
            try {
                Optional<QuoteDto> optional = completableFuture.get();
                if (optional.isPresent()) {
                    Optional<Quote> saved = save(QuoteDto.toEntity(optional.get())).blockOptional();
                    saved.ifPresent(quote -> result.add(quote.toDto()));
                }
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }
        executorService.shutdown();
        return result;
    }

    @Transactional
    public Mono<Quote> save(Quote quote) {
        Quote lastQuote = quoteRepository.findLastQuoteBySymbol(quote.getSymbol()).blockFirst();
        if (lastQuote == null || Double.compare(lastQuote.getLatestPrice(), quote.getLatestPrice()) != 0) {
            return quoteRepository.save(quote);
        }
        return Mono.empty();
    }

    public List<Quote> getTopQuotes() {
        return quoteRepository.findTopStocks().collectList().blockOptional().orElse(Collections.emptyList());
    }
}
