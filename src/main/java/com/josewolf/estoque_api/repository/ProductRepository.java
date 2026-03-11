package com.josewolf.estoque_api.repository;

import com.josewolf.estoque_api.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findByCategoryCategoryNameIgnoreCase(String categoryName);

    List<Product> findByProductNameContainingIgnoreCase(String productName);



}
