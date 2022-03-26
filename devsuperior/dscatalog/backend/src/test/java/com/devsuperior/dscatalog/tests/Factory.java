package com.devsuperior.dscatalog.tests;

import com.devsuperior.dscatalog.dto.ProductDto;
import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.entities.Product;

import java.time.Instant;

public class Factory {

    public static Product createProduct() {
        Product product = new Product(1L, "Phone", "Good Phone ", 4170.0, "https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/25-big.jpg", Instant.parse("2020-07-14T10:00:00Z"));
        product.getCategories().add(new Category(2l, "Electronics"));
        return product;
    }

    public static ProductDto createProductDto() {
        Product product = createProduct();
        return new ProductDto(product, product.getCategories());
    }
}
