package stocks.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import stocks.dto.SymbolDto;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Symbol {
    @Id
    private Long id;
    private String symbol;
    private String name;
    private LocalDate date;
    private Boolean isEnabled;

    public SymbolDto toDto() {
        SymbolDto dto = new SymbolDto();
        dto.setId(id);
        dto.setSymbol(symbol);
        dto.setName(name);
        dto.setDate(date);
        dto.setIsEnabled(isEnabled);
        return dto;
    }
}
