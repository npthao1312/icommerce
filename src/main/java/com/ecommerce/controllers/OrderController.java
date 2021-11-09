package com.ecommerce.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ecommerce.models.Order;
import com.ecommerce.models.Product;
import com.ecommerce.repository.OrderRepository;
import com.ecommerce.repository.ProductRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
	@Autowired
	ProductRepository productRepository;

	@Autowired
	OrderRepository orderRepository;

	@Operation(summary = "List all orders", tags = { "Order" })
	@ApiResponses(value = @ApiResponse(description = "successful operation"))
	@GetMapping(value = { "" })
	public ResponseEntity<List<Order>> getAllOrders() {
		try {
			List<Order> orders = new ArrayList<Order>();

			orderRepository.findAll().forEach(orders::add);

			if (orders.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(orders, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Operation(summary = "Add a product to a order", tags = { "Order" })
	@ApiResponses(value = @ApiResponse(description = "successful operation"))
	@PutMapping("/{orderId}/products/{productId}")
	public ResponseEntity<Order> addProduct(@PathVariable("orderId") Long orderId,
			@PathVariable("productId") Long productId) throws JsonMappingException, JsonProcessingException {
		Optional<Order> orderData = orderRepository.findById(orderId);
		Optional<Product> productData = productRepository.findById(productId);
		if (productData.isPresent() && orderData.isPresent()) {
			Order _order = orderData.get();
			Product _product = productData.get();
			_product.setOrder(_order);
			productRepository.save(_product);
			return new ResponseEntity<>(orderRepository.save(_order), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}