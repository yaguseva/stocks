package stocks.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;
import stocks.util.LocalDateDeserializer;
import stocks.entity.Quote;

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

    public static Quote toEntity(QuoteDto dto) {
        Quote quote = new Quote();
        quote.setVolume(dto.volume);
        quote.setPreviousVolume(dto.previousVolume);
        quote.setLatestPrice(dto.latestPrice);
        quote.setChange(dto.change);
        quote.setCalculationPrice(dto.calculationPrice);
        quote.setCompanyName(dto.companyName);
        quote.setCurrency(dto.currency);
        quote.setSymbol(dto.getSymbol());
        quote.setLatestUpdate(dto.getLatestUpdate());
        return quote;
    }
}
