package com.example.dddtest.persistence;

import com.example.dddtest.domain.MonthlyBudget;
import com.example.dddtest.domain.MonthlyBudgetId;
import org.springframework.data.repository.CrudRepository;

public interface MonthlyBudgetRepository extends CrudRepository<MonthlyBudget, MonthlyBudgetId> {
}
