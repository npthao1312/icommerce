package com.ecommerce;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.ecommerce.models.Product;
import com.ecommerce.repository.CategoryRepository;
import com.ecommerce.repository.ProductRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class JPAUnitTest {
	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	ProductRepository productRepository;

	@Autowired
	CategoryRepository categoryRepository;

	@Test
	public void should_find_no_products_if_repository_is_empty() {
		Iterable<Product> products = productRepository.findAll();

		assertThat(products).isEmpty();
	}

	@Test
	public void should_store_a_product() {
		Product product = productRepository.save(new Product("iPhone", "Apple", "rose gold", 500.00));

		assertThat(product).hasFieldOrPropertyWithValue("name", "iPhone");
		assertThat(product).hasFieldOrPropertyWithValue("brand", "Apple");
		assertThat(product).hasFieldOrPropertyWithValue("color", "rose golde");
		assertThat(product).hasFieldOrPropertyWithValue("price", 500.00);
	}
}
