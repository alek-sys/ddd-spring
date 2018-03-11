package com.example.dddtest.budgets.integration;

import com.example.dddtest.budgets.domain.MonthlyBudget;
import com.example.dddtest.budgets.domain.MonthlyBudgetId;
import org.springframework.data.repository.CrudRepository;

interface MonthlyBudgetRepository extends CrudRepository<MonthlyBudget, MonthlyBudgetId> {
}
