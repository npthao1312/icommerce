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
import com.ecommerce.models.Category;
import com.ecommerce.repository.CategoryRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/categories")
public class CategoryController {
	@Autowired
	CategoryRepository categoryRepository;

	@Operation(summary = "List all categories", tags = { "Category" })
	@ApiResponses(value = @ApiResponse(description = "successful operation"))
	@GetMapping(value = { "" })
	public ResponseEntity<List<Category>> getAllCategorys(@RequestParam(required = false) String title) {
		try {
			List<Category> categorys = new ArrayList<Category>();

			categoryRepository.findAll().forEach(categorys::add);

			if (categorys.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(categorys, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Operation(summary = "Create a product", tags = { "Category" })
	@ApiResponses(value = @ApiResponse(description = "successful operation"))
	@PostMapping(value = { "" })
	public ResponseEntity<Category> createCategory(@RequestBody Category category) {
		try {
			Category _category = categoryRepository.save(new Category(category.getName()));
			return new ResponseEntity<>(_category, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}