package stocks.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import stocks.dto.QuoteDto;

import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Quote {
    @Id
    private Long id;
    private Long volume;
    private Long previousVolume;
    private Double latestPrice;
    private Double change;
    private String calculationPrice;
    private String companyName;
    private String currency;
    private String symbol;
    private LocalDate latestUpdate;

    public QuoteDto toDto() {
        QuoteDto dto = new QuoteDto();
        dto.setId(id);
        dto.setVolume(volume);
        dto.setPreviousVolume(previousVolume);
        dto.setLatestPrice(latestPrice);
        dto.setChange(change);
        dto.setCalculationPrice(calculationPrice);
        dto.setCompanyName(companyName);
        dto.setCurrency(currency);
        dto.setSymbol(symbol);
        dto.setLatestUpdate(latestUpdate);
        return dto;
    }
}
