package stocks.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import stocks.client.IexApiClient;
import stocks.dto.SymbolDto;
import stocks.entity.Symbol;
import stocks.repository.SymbolRepository;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SymbolService {
    private final IexApiClient iexApiClient;
    private final SymbolRepository symbolRepository;

    public Flux<Symbol> process() {
        Flux<Symbol> symbols = iexApiClient.getAllSymbols().map(SymbolDto::toEntity);
        return symbolRepository.saveAll(symbols);
//        List<Symbol> listMono = saved.collectList().block();
//        log.info("" + listMono.get(0));
//        return saved;
    }

    public Flux<SymbolDto> getAll() {
        return symbolRepository.findAll().map(Symbol::toDto);
    }
}
