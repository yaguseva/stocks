package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity(name="stocks")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class StockEntity {
    @Id
    private String symbol;
    @Column(name = "company_name")
    private String companyName;
    @Column(name = "change_percent")
    private float changePercent;
    @Column(name = "latest_price")
    private float latestPrice;

    @Override
    public String toString() {
        return "Stock{" +
                "symbol='" + symbol + '\'' +
                ", companyName='" + companyName + '\'' +
                ", changePercent=" + changePercent +
                ", latestPrice=" + latestPrice +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StockEntity company = (StockEntity) o;
        return Float.compare(company.changePercent, changePercent) == 0 && Float.compare(company.latestPrice, latestPrice) == 0 && symbol.equals(company.symbol) && companyName.equals(company.companyName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(symbol, companyName, changePercent, latestPrice);
    }
}
