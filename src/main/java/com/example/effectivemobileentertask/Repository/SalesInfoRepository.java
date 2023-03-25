package com.example.effectivemobileentertask.Repository;

import com.example.effectivemobileentertask.Entity.SalesInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalesInfoRepository extends JpaRepository<SalesInfo, Long> {
}
