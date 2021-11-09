package com.ecommerce.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import com.ecommerce.models.Product;
import com.ecommerce.repository.ProductRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/products")
public class ProductController {
	@Autowired
	ProductRepository productRepository;

	@Operation(summary = "List all products", tags = { "Product" })
	@ApiResponses(value = @ApiResponse(description = "successful operation"))
	@GetMapping(value = { "" })
	public ResponseEntity<List<Product>> getAllProducts(@RequestParam(required = false) String keyword) {
		try {
			List<Product> products = new ArrayList<Product>();

			if (keyword == null)
				productRepository.findAll().forEach(products::add);
			else
				productRepository.search(keyword).forEach(products::add);

			if (products.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(products, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Operation(summary = "Create a product", tags = { "Product" })
	@ApiResponses(value = @ApiResponse(description = "successful operation"))
	@PostMapping(value = { "" })
	public ResponseEntity<Product> createProduct(@RequestBody Product product) {
		try {
			Product _product = productRepository
					.save(new Product(product.getName(), product.getBrand(), product.getColor(), product.getPrice()));
			return new ResponseEntity<>(_product, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}