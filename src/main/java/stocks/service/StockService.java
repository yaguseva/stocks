package stocks.service;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import stocks.dto.SymbolDto;

import java.util.List;

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
}
