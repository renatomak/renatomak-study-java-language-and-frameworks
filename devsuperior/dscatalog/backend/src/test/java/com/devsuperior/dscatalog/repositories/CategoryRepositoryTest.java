package com.devsuperior.dscatalog.repositories;

import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.tests.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    private long existingId;
    private long nonexistingId;
    private long countTotalCategories;

    @BeforeEach
    void setUp() throws Exception {
        existingId = 1L;
        nonexistingId = 1000L;
        countTotalCategories = 3;
    }

    @Test
    public void saveShouldPersistWithAutoIncrementWhenIdIsNull() {
        Category category = Factory.createCategory();
        category.setId(null);
        category = categoryRepository.save(category);

        Assertions.assertNotNull(category.getId());
        Assertions.assertEquals(countTotalCategories + 1, category.getId());
    }

    @Test
    public void deleteShouldDeleteObjectWhenIdExists() {
        // Act
        categoryRepository.deleteById(existingId);
        Optional<Category> result = categoryRepository.findById(existingId);

        // Assert
        Assertions.assertFalse(result.isPresent());
    }

    @Test
    public void deleteShouldThrowEmptyResultDataAccessExceptionWhenIdDoesNotExist() {
        Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
            categoryRepository.deleteById(nonexistingId);
        });
    }

    @Test
    public void findByIdShouldReturnOptionalNotEmpetyWhenIdExists() {
        // Act
        Optional<Category> result = categoryRepository.findById(existingId);

        // Assert
        Assertions.assertTrue(result.isPresent());
    }

    @Test
    public void findByIdShouldReturnOptionalEmpetyWhenIdDoesNotExists() {
        // Act
        Optional<Category> result = categoryRepository.findById(nonexistingId);

        // Assert
        Assertions.assertFalse(result.isPresent());
    }

    @Test
    public void findAllPagedShouldReturnPage() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);

        Page<Category> result = categoryRepository.findAll(pageable);

        Assertions.assertNotNull(result);
    }

}