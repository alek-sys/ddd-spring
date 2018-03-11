package com.example.dddtest.spends.domain;

import lombok.EqualsAndHashCode;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
public class SpendCategoryId implements Serializable {
    SpendCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    private SpendCategoryId() {}

    String categoryId;

    public static SpendCategoryId of(String categoryId) {
        return new SpendCategoryId(categoryId);
    }
}
