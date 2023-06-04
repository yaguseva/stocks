package stocks.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class StockQuoteDto {
    private Long avgTotalVolume;
    private Long volume;
    private String calculationPrice;
    private Double change;
    private String companyName;
    private String currency;
    private String symbol;
}
