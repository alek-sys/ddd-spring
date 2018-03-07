package com.example.dddtest.domain;

import lombok.EqualsAndHashCode;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.Month;

@Embeddable
@EqualsAndHashCode
public class MonthlyBudgetId implements Serializable {
    Month month;
    Long year;

    private MonthlyBudgetId() {}

    private MonthlyBudgetId(Month month, Long year) {
        this.month = month;
        this.year = year;
    }

    public static MonthlyBudgetId of(Month month, Long year) {
        return new MonthlyBudgetId(month, year);
    }

    public static MonthlyBudgetId of(LocalDateTime dateTime) {
        return of(dateTime.getMonth(), (long) dateTime.getYear());
    }
}
