package com.example.dddtest.spends.integration;

import com.example.dddtest.spends.domain.SpendCategory;
import com.example.dddtest.spends.domain.SpendCategoryId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
interface SpendCategoriesRepository extends CrudRepository<SpendCategory, SpendCategoryId> {

}
