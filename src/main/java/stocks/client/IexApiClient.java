package stocks.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import stocks.dto.QuoteDto;
import stocks.dto.SymbolDto;

@Slf4j
@Component
@RequiredArgsConstructor
public class IexApiClient {

    @Value("${iex.api.baseurl}")
    private String iexApiBaseUrl;

    @Value("${iex.api.token}")
    private String iexApiToken;

    private final WebClient webClient;

    public Flux<SymbolDto> getAllSymbols() {
        log.info("Request all symbols");
        return webClient.get()
                .uri(builder -> builder.path(iexApiBaseUrl + "/data/core/ref_data_iex_symbols")
                        .queryParam("token", iexApiToken)
                        .build())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .exchangeToFlux(response -> response.bodyToFlux(SymbolDto.class));
    }

    public Flux<QuoteDto> getQuoteBySymbol(String symbol) {
        log.info("Request quote for " + symbol);
        return webClient.get()
                .uri(builder -> builder.path(iexApiBaseUrl + "/data/core/quote/" + symbol)
                        .queryParam("token", iexApiToken)
                        .build())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .exchangeToFlux(response -> {
                    if (HttpStatus.OK.equals(response.statusCode())) {
                        return response.bodyToFlux(QuoteDto.class);
                    } else {
                        log.info("Request failed with code " + response.statusCode());
                        return Flux.empty();
                    }
                });
    }
}
