package com.amit.oracle.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.amit.oracle.entity.Product;

public interface ProductRepo  extends JpaRepository<Product, Integer>{

}
