package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SymbolDto {
    private String symbol;
    private Date date;
    private boolean isEnable;
    private String name;
}
