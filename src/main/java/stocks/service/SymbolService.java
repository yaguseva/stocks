package stocks.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import stocks.client.IexApiClient;
import stocks.dto.SymbolDto;
import stocks.entity.Symbol;
import stocks.repository.SymbolRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class SymbolService {
    private final IexApiClient iexApiClient;
    private final SymbolRepository symbolRepository;

    @Transactional
    public Flux<Symbol> process() {
        Flux<Symbol> symbols = iexApiClient.getAllSymbols().map(SymbolDto::toEntity);
        return symbolRepository.saveAll(symbols);
    }
}
