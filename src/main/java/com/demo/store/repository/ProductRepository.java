package com.demo.store.repository;

import com.demo.store.model.Product;
import com.demo.store.model.ProductState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p FROM Product p WHERE (:state IS NULL OR p.state = :state)")
    Page<Product> findAllByOptionalState(@Param("state") ProductState state, Pageable pageable);
}
