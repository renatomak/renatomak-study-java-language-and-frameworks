package com.devsuperior.dscatalog.services;

import com.devsuperior.dscatalog.dto.ProductDto;
import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.entities.Product;
import com.devsuperior.dscatalog.repositories.CategoryRepository;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    private long existingId;
    private long nonExistingId;
    private long depndentId;
    private PageImpl<Product> page;
    private Product product;
    private ProductDto productDto;
    private Category category;

    @BeforeEach
    void setUp() throws Exception{
        existingId = 1L;
        nonExistingId = 1000L;
        depndentId = 4L;
        product = Factory.createProduct();
        page = new PageImpl<>(List.of(product));
        productDto = Factory.createProductDto();
        category = Factory.createCategory();


        when(productRepository.getById(existingId)).thenReturn(product);
        when(productRepository.getById(nonExistingId)).thenThrow(EntityNotFoundException.class);

        when(categoryRepository.getById(existingId)).thenReturn(category);
        when(categoryRepository.getById(nonExistingId)).thenThrow(EntityNotFoundException.class);

        when(productRepository.findById(existingId)).thenReturn(Optional.of(product));
        when(productRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        when(productRepository.save(ArgumentMatchers.any())).thenReturn(product);

        when(productRepository.findAll((Pageable) ArgumentMatchers.any())).thenReturn(page);

        doNothing().when(productRepository).deleteById(existingId);
        doThrow(ResourceNotFoundException.class).when(productRepository).deleteById(nonExistingId);
        doThrow(DataIntegrityViolationException.class).when(productRepository).deleteById(depndentId);
    }

    @Test
    public void updateShouldReturnProductDtoWhenIdExists() {
        ProductDto result = productService.findById(existingId);
        Assertions.assertEquals(productDto, result);
        verify(productRepository, times(1)).findById(existingId);
    }

    @Test
    public void updateShouldThrowResourceNotFoundExceptionWhenIdDoesNotExists() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            productService.update(nonExistingId, productDto);
        });
    }

    @Test
    public void findByIdShouldReturnProductDtoWhenIdExists() {
        ProductDto result = productService.findById(existingId);
        Assertions.assertEquals(productDto, result);
        Assertions.assertNotNull(result);
        verify(productRepository, times(1)).findById(existingId);
    }

    @Test
    public void findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExists() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            productService.findById(nonExistingId);
        });
        verify(productRepository, times(1)).findById(nonExistingId);
    }

    @Test
    public void deleteShouldDoNothingWhenIdExists() {
        // Assert
        Assertions.assertDoesNotThrow(() -> {
            productService.delete(existingId);
        });

        verify(productRepository, times(1)).deleteById(existingId);
    }

    @Test
    public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExists() {
        // Assert
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            productService.delete(nonExistingId);
        });

        verify(productRepository, times(1)).deleteById(nonExistingId);
    }

    @Test
    public void findAllPagedShouldReturnPage() {
        //
        Pageable pageable = PageRequest.of(0, 10);

        // Act
        Page<ProductDto> result = productService.findAllPaged(pageable);

        // Assert
        Assertions.assertNotNull(result);
        verify(productRepository, times(1)).findAll(pageable);

    }

    @Test
    public void deleteShouldThrowDataBasesExceptionWhenDependentId() {
        // Assert
        Assertions.assertThrows(DataBasesException.class, () -> {
            productService.delete(depndentId);
        });

        verify(productRepository, times(1)).deleteById(depndentId);
    }
}