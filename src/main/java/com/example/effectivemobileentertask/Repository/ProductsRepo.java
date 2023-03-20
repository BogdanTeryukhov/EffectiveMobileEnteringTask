package com.example.effectivemobileentertask.Repository;

import com.example.effectivemobileentertask.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductsRepo extends JpaRepository<Product, Long> {
}
