package stocks.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import stocks.entity.Symbol;

@Repository
public interface SymbolRepository extends ReactiveCrudRepository<Symbol, Long> {
    @Query("SELECT * FROM SYMBOL WHERE SYMBOL = $1")
    Flux<Symbol> findBySymbol(String symbol);
}
