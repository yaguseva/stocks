package stocks.dto;

import lombok.*;
import stocks.entity.Symbol;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SymbolDto {
    private Long id;
    private String symbol;
    private String name;
    private Date date;
    private Boolean isEnabled;

    public Symbol toEntity() {
        Symbol result = new Symbol();
        result.setId(id);
        result.setSymbol(symbol);
        result.setName(name);
        result.setDate(date);
        result.setIsEnabled(isEnabled);
        return result;
    }
}
