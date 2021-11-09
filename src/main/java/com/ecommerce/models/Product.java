package com.ecommerce.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "products")
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	@Size(max = 150)
    private String name;

	@NotBlank
    private Double price;      

	@NotBlank
	@Size(max = 150)
    private String brand;
    
	@NotBlank
	@Size(max = 50)
    private String color;
    
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(	name = "product_categories", 
				joinColumns = @JoinColumn(name = "product_id"), 
				inverseJoinColumns = @JoinColumn(name = "category_id"))
	private Set<Category> categories = new HashSet<>();
    
	@ManyToOne
	@JoinColumn(name = "order_id")
	@JsonIgnore	
	private Order order;
	
	
	public Product() {
	}

	public Product(String name, String brand, String color, Double price) {
		this.name = name;
		this.brand = brand;
		this.color = color;
		this.price = price;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}
	
	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
	
	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
	
	public Order getOrder() {
		return order;
	}

	public void setOrder(Order orderr) {
		this.order = order;
	}

	
	public Set<Category> getCategories() {
		return categories;
	}

	public void setCategories(Set<Category> categories) {
		this.categories = categories;
	}
}
