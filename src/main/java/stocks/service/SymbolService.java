package stocks.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import stocks.dto.SymbolDto;
import stocks.entity.Symbol;
import stocks.repository.SymbolRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SymbolService {
    private final WebClient webClient;
    private final SymbolRepository symbolRepository;

    public List<SymbolDto> getAvailableSymbols() {
        Optional<List<SymbolDto>> symbols = webClient.get()
                .uri(builder -> builder.path("/data/core/ref_data_iex_symbols")
                        .queryParam("token", "pk_d209595a36ab41c9a69d4672f428038c")
                        .build())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .exchangeToMono(response -> response.bodyToMono(new ParameterizedTypeReference<List<SymbolDto>>() {
                }))
                .blockOptional();
        if (symbols.isPresent()) {
            return saveSymbols(symbols.get());
        }
        return Collections.emptyList();
    }

    @Transactional
    public List<SymbolDto> saveSymbols(List<SymbolDto> dtos) {
        List<SymbolDto> result = new ArrayList<>(dtos.size());
        for (SymbolDto dto : dtos) {
            Optional<List<Symbol>> fromDb = symbolRepository.findBySymbol(dto.getSymbol()).collectList().blockOptional();
            if (fromDb.isEmpty() || fromDb.get().isEmpty()) {
                Symbol saved = symbolRepository.save(dto.toEntity()).block();
                result.add(saved.toDto());
            } else {
                result.add(fromDb.get().get(0).toDto());
            }
        }
        return result;
    }
}
