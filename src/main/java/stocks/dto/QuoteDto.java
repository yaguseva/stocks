package stocks.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;
import stocks.entity.Quote;
import stocks.util.LocalDateDeserializer;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class QuoteDto {
    private Long id;
    private Long volume;
    private Long previousVolume;
    private Double latestPrice;
    private Double change;
    private String calculationPrice;
    private String companyName;
    private String currency;
    private String symbol;
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate latestUpdate;

    public Quote toEntity() {
        Quote quote = new Quote();
        quote.setVolume(volume);
        quote.setPreviousVolume(previousVolume);
        quote.setLatestPrice(latestPrice);
        quote.setChange(change);
        quote.setCalculationPrice(calculationPrice);
        quote.setCompanyName(companyName);
        quote.setCurrency(currency);
        quote.setSymbol(getSymbol());
        quote.setLatestUpdate(getLatestUpdate());
        return quote;
    }
}
