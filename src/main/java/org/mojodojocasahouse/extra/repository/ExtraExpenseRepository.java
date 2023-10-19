package org.mojodojocasahouse.extra.repository;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.mojodojocasahouse.extra.model.ExtraExpense;
import org.mojodojocasahouse.extra.model.ExtraUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ExtraExpenseRepository extends JpaRepository<ExtraExpense, Long>{
    List<ExtraExpense> findAllExpensesByUser(@Param("userId") ExtraUser user);

    Optional<ExtraExpense> findFirstByConcept(String string);

    List<ExtraExpense> findAllExpensesByUserAndCategory(ExtraUser user, String category);

    @Query("SELECT DISTINCT e.category FROM ExtraExpense e WHERE e.user = :userId")
    List<String> findAllDistinctCategoriesByUser(@Param("userId") ExtraUser user);

    Optional<ExtraExpense> findByCategory(String category);

    boolean existsByIdAndUser(Long id, ExtraUser user);

    @Query("SELECT COALESCE(SUM(e.amount), 0) " + "FROM ExtraExpense e " + "WHERE e.user = :user " + "AND e.category = :category " + "AND e.date BETWEEN :minDate AND :maxDate")
    BigDecimal getSumOfExpensesOfAnUserByCategoryAndDateInterval(@Param("user") ExtraUser user, @Param("category") String category, @Param("minDate") Date minDate,@Param("maxDate") Date maxDate
    );
}

