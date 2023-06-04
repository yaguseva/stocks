package stocks.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SymbolDto {
    private String symbol;
    private String name;
    private LocalDate date;
    private boolean isEnabled;
}
