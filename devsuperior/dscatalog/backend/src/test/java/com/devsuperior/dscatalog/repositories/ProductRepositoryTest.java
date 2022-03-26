package com.devsuperior.dscatalog.repositories;

import com.devsuperior.dscatalog.entities.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void deleteShouldDeleteObjectWhenIdExists() {
        // Arrange
        long existingId = 1L;

        //
        productRepository.deleteById(existingId);
        Optional<Product> result = productRepository.findById(existingId);

        // Assert
        Assertions.assertFalse(result.isPresent());

    }

    @Test
    public void deleteShouldThrowEmptyResultDataAccessExceptionWhenIdDoesNotExist() {
        // Arrange
        long nonexistingId = 1000L;

        // Assert
        Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
           productRepository.deleteById(nonexistingId);
        });

    }
}