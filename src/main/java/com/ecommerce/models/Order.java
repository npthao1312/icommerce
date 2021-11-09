package com.ecommerce.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private User customerId;

    @JsonFormat(pattern = "dd/MM/yyyy") 
    private LocalDate dateCreated;

    private String status;

	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<Product> orderProducts = new HashSet<>();

    @Transient
    public Double getTotalOrderPrice() {
        double sum = 0D;
        Set<Product> orderProducts = getOrderProducts();
        for (Product op : orderProducts) {
            sum += op.getPrice();
        }

        return sum;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public User getCustomerId() {
        return customerId;
    }

    public void setCustmerId(User customerId) {
        this.customerId = customerId;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Set<Product> getOrderProducts() {
        return orderProducts;
    }

    public void setOrderProducts(Set<Product> orderProducts) {
        this.orderProducts = orderProducts;
    }
}