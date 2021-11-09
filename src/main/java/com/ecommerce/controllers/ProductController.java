package com.ecommerce.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

	@Operation(summary = "Create a product", security = { @SecurityRequirement(name = "bearer-key") }, tags = {
			"Product" })
	@ApiResponses(value = @ApiResponse(description = "successful operation"))
	@PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
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

	@GetMapping("/{id}")
	public ResponseEntity<Product> getProductById(@PathVariable("id") long id) {
		Optional<Product> productData = productRepository.findById(id);

		if (productData.isPresent()) {
			return new ResponseEntity<>(productData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@Operation(summary = "Delete a product", security = { @SecurityRequirement(name = "bearer-key") }, tags = {
			"Product" })
	@ApiResponses(value = @ApiResponse(description = "successful operation"))
	@PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<HttpStatus> deleteProduct(@PathVariable("id") long id) {
		try {
			productRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Operation(summary = "Delete all product", security = { @SecurityRequirement(name = "bearer-key") }, tags = {
			"Product" })
	@ApiResponses(value = @ApiResponse(description = "successful operation"))
	@PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
	@DeleteMapping("")
	public ResponseEntity<HttpStatus> deleteAllProducts() {
		try {
			productRepository.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}