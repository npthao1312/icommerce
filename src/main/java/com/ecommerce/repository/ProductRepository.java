package com.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ecommerce.models.Product;

@Repository
public interface ProductRepository  extends JpaRepository<Product, Long>{
	@Query("SELECT p FROM Product p WHERE CONCAT(p.name, p.brand, p.color, p.price) LIKE %?1%")
	public List<Product> search(String keyword);
}
