package stocks.dto;

import lombok.*;
import stocks.entity.Quote;

import java.util.Date;

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
    private Date latestUpdate;

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
