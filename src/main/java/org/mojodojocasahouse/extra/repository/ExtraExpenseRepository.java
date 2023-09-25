package org.mojodojocasahouse.extra.repository;
import org.mojodojocasahouse.extra.model.ExtraExpense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExtraExpenseRepository extends JpaRepository<ExtraExpense, Long>{
}
