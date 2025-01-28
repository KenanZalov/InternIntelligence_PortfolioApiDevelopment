package org.example.portfolioapidevelopment.repository;

import org.example.portfolioapidevelopment.dao.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {
    boolean existsByTitle(String title);
    List<Portfolio> findByMyUserId(Long userId);
}
