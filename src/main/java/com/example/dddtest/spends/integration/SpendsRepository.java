package com.example.dddtest.spends.integration;

import com.example.dddtest.spends.domain.Spend;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
interface SpendsRepository extends CrudRepository<Spend, Long> {
}
