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

    @Query("WITH stat AS (" +
            "    SELECT *, " +
            "       COALESCE(VOLUME, PREVIOUS_VOLUME) vol," +
            "           ROW_NUMBER() OVER(PARTITION BY q.SYMBOL ORDER BY q.LATEST_UPDATE DESC) AS rank" +
            "      FROM QUOTE q)" +
            " SELECT *" +
            "   FROM stat" +
            " WHERE rank = 1" +
            " ORDER BY vol DESC, COMPANY_NAME DESC " +
            " LIMIT 5")
    Flux<Quote> findTopStocks();
}
