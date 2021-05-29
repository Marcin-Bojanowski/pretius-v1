package com.example.demo.repositories;

import com.example.demo.model.CurrencyHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
@Repository
public interface CurrencyHistoryRepository extends JpaRepository<CurrencyHistory,Long> {
}
