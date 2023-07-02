package com.example.demo.repository;

import com.example.demo.model.StockEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface StockRepository extends JpaRepository<StockEntity, String> {

    @Query(value = "select * from stocks \n" +
            "order by latest_price desc limit 5", nativeQuery = true)
    List<StockEntity> findTop5ByLatestPrice();

    @Query(value = """
            select * 
            from stocks
            order by Abs(change_percent)  desc
            limit 5
            """, nativeQuery = true)
    List<StockEntity> findTop5ByChangePercent();

    @Query("select c from stocks c")
    List<StockEntity> getAllCompanies();
}
