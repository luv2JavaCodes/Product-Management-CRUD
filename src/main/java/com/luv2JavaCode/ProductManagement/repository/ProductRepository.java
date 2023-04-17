package com.luv2JavaCode.ProductManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luv2JavaCode.ProductManagement.model.Products;

public interface ProductRepository extends JpaRepository<Products, Long> {

}
