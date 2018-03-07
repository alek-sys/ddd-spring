package com.example.dddtest.persistence;

import com.example.dddtest.domain.Spend;
import com.example.dddtest.domain.SpendCategoryId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpendsRepository extends CrudRepository<Spend, Long> {
}
