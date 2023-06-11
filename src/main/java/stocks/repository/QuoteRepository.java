package stocks.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import stocks.entity.Quote;

@Repository
public interface QuoteRepository extends ReactiveCrudRepository<Quote, Long> {

    @Query("SELECT * FROM QUOTE WHERE SYMBOL = $1 ORDER BY LATEST_UPDATE DESC LIMIT 1")
    Flux<Quote> findLastQuoteBySymbol(String symbol);
}
