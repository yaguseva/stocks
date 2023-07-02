package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Entity(name="companies")
public class CompanyEntity {
    @Id
    private String symbol;
    private Date date;
    private boolean isEnable;
    private String name;

    @Override
    public String toString() {
        return "Company{" +
                "symbol='" + symbol + '\'' +
                ", date=" + date +
                ", isEnable=" + isEnable +
                ", name='" + name + '\'' +
                '}';
    }
}
