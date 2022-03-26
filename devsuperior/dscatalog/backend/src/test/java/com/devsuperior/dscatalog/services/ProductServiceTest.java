package com.devsuperior.dscatalog.services;

import com.devsuperior.dscatalog.entities.Product;
import com.devsuperior.dscatalog.repositories.ProductRepository;
import com.devsuperior.dscatalog.services.exceptions.DataBasesException;
import com.devsuperior.dscatalog.services.exceptions.ResourceNotFoundException;
import com.devsuperior.dscatalog.tests.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    private long existingId;
    private long nonExistingId;
    private long depndentId;
    private PageImpl<Product> page;
    private Product product;

    @BeforeEach
    void setUp() throws Exception{
        existingId = 1L;
        nonExistingId = 1000L;
        depndentId = 4L;
        product = Factory.createProduct();
        page = new PageImpl<>(List.of(product));

        Mockito.when(productRepository.findById(existingId)).thenReturn(Optional.of(product));
        Mockito.when(productRepository.findById(nonExistingId)).thenReturn(Optional.empty());
        Mockito.when(productRepository.save(ArgumentMatchers.any())).thenReturn(product);
        Mockito.when(productRepository.findAll((Pageable) ArgumentMatchers.any())).thenReturn(page);
        doNothing().when(productRepository).deleteById(existingId);
        doThrow(ResourceNotFoundException.class).when(productRepository).deleteById(nonExistingId);
        doThrow(DataIntegrityViolationException.class).when(productRepository).deleteById(depndentId);
    }

    @Test
    public void deleteShouldDoNothingWhenIdExists() {
        // Assert
        Assertions.assertDoesNotThrow(() -> {
            productService.delete(existingId);
        });

        verify(productRepository, Mockito.times(1)).deleteById(existingId);
    }

    @Test
    public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExists() {
        // Assert
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            productService.delete(nonExistingId);
        });

        verify(productRepository, Mockito.times(1)).deleteById(nonExistingId);
    }

    @Test
    public void deleteShouldThrowDataBasesExceptionWhenDependentId() {
        // Assert
        Assertions.assertThrows(DataBasesException.class, () -> {
            productService.delete(depndentId);
        });

        verify(productRepository, Mockito.times(1)).deleteById(depndentId);
    }
}