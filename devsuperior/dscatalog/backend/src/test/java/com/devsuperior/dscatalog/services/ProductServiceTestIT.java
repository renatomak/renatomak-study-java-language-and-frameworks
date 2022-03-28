package com.devsuperior.dscatalog.services;

import com.devsuperior.dscatalog.dto.ProductDto;
import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.entities.Product;
import com.devsuperior.dscatalog.repositories.ProductRepository;
import com.devsuperior.dscatalog.services.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ProductServiceTestIT {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    private long existingId;
    private long nonExistingId;
    private long depndentId;
    private int countTotalProducts;
    private PageImpl<Product> page;
    private Product product;
    private ProductDto productDto;
    private Category category;

    @BeforeEach
    void setUp() throws Exception {
        existingId = 1L;
        nonExistingId = 1000L;
        countTotalProducts = 25;

    }

    @Test
    void findAllPaged() {
    }

    @Test
    void findById() {
    }

    @Test
    void create() {
    }

    @Test
    void update() {
    }

    @Test
    void deleteShouldDeleteResourceWhenIdExists() {
        productService.delete(existingId);

        Assertions.assertEquals(countTotalProducts - 1, productRepository.count());
    }

    @Test
    void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExists() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            productService.delete(nonExistingId);
        });
    }

    @Test void findAllPagedShouldReturnPageWhenPage0Size10() {
        PageRequest pageRequest = PageRequest.of(0, 10);

       Page<ProductDto> result =  productService.findAllPaged(pageRequest);

       Assertions.assertFalse(result.isEmpty());
       Assertions.assertEquals(0, result.getNumber());
       Assertions.assertEquals(10, result.getSize());
       Assertions.assertEquals(countTotalProducts, result.getTotalElements());
    }

    @Test void findAllPagedShouldReturnEmptyPageWhenPageDoesNotExist() {
        PageRequest pageRequest = PageRequest.of(50, 10);

       Page<ProductDto> result =  productService.findAllPaged(pageRequest);

       Assertions.assertTrue(result.isEmpty());
    }

    @Test void findAllPagedShouldReturnSortedPageWhenSortByName() {
        PageRequest pageRequest = PageRequest.of(0, 10, Sort.by("name"));

       Page<ProductDto> result =  productService.findAllPaged(pageRequest);

       Assertions.assertFalse(result.isEmpty());
       Assertions.assertEquals("Macbook Pro", result.getContent().get(0).getName());
       Assertions.assertEquals("PC Gamer", result.getContent().get(1).getName());
       Assertions.assertEquals("PC Gamer Alfa", result.getContent().get(2).getName());
    }
}