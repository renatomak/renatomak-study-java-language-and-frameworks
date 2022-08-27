package com.devsuperior.dscatalog.services;

import com.devsuperior.dscatalog.dto.CategoryDto;
import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.repositories.CategoryRepository;
import com.devsuperior.dscatalog.services.exceptions.DataBasesException;
import com.devsuperior.dscatalog.services.exceptions.ResourceNotFoundException;
import com.devsuperior.dscatalog.tests.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class          CategoryServiceTest {

    @InjectMocks
    private CategoryService categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    private PageImpl<Category> page;
    private Category category;
    private Long existsId;
    private Long nonExistsId;
    private Long dependentId;
    private CategoryDto categoryDto;

    @BeforeEach
    void setUp() throws Exception {
        existsId = 1L;
        nonExistsId = 2L;
        dependentId = 3L;
        categoryDto = Factory.createCategoryDto();
        category = Factory.createCategory();
        page = new PageImpl<>(List.of(category));

        Mockito.when(categoryRepository.findAll((Pageable) ArgumentMatchers.any())).thenReturn(page);

        Mockito.when(categoryRepository.findById(existsId)).thenReturn(Optional.of(category));
        Mockito.when(categoryRepository.findById(nonExistsId)).thenThrow(ResourceNotFoundException.class);

        Mockito.when(categoryRepository.save(category)).thenReturn(category);

        Mockito.when(categoryRepository.getById(existsId)).thenReturn(category);
        Mockito.when(categoryRepository.getById(nonExistsId)).thenThrow(ResourceNotFoundException.class);

        doNothing().when(categoryRepository).deleteById(existsId);
        doThrow(ResourceNotFoundException.class).when(categoryRepository).deleteById(nonExistsId);
        doThrow(DataBasesException.class).when(categoryRepository).deleteById(dependentId);
    }

    @Test
    void deleteShouldThrowDataBasesExceptionWhenIdIsDependent() {
        Assertions.assertThrows(DataBasesException.class, () -> {
            categoryService.delete(dependentId);
        });
        verify(categoryRepository, times(1)).deleteById(dependentId);
    }

    @Test
    void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExists() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            categoryService.delete(nonExistsId);
        });
        verify(categoryRepository, times(1)).deleteById(nonExistsId);
    }

    @Test
    void deleteShouldDoNothingWhenIdExists() {
        Assertions.assertDoesNotThrow(() -> {
            categoryService.delete(existsId);
        });
        verify(categoryRepository, times(1)).deleteById(existsId);
    }

    @Test
    void updateShouldReturnCategoryDtoWhenIdExists() {
        // Act
        CategoryDto result = categoryService.update(existsId, categoryDto);

        // Assert
        Assertions.assertNotNull(result);
        verify(categoryRepository, times(1)).save(category);
    }

    @Test
    void updateShouldThrowResourceNotFoundExceptionWhenIdDoesNotExists() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            categoryService.update(nonExistsId, categoryDto);
        });

        verify(categoryRepository, times(1)).getById(nonExistsId);
    }

    @Test
    void createShouldReturnCategoryDto() {
        // Act
        CategoryDto result = categoryService.create(categoryDto);

        // Assert
        Assertions.assertNotNull(result);
        verify(categoryRepository, times(1)).save(category);
    }


    @Test
    public void findAllShouldReturnPage() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);

        // Act
        Page<CategoryDto> result = categoryService.findAllPaged(pageable);

        // Assert
        Assertions.assertNotNull(result);
        verify(categoryRepository, times(1)).findAll(pageable);
    }

    @Test
    void findByIdShouldReturnCategoryDtoWhenIdExixts() {
        CategoryDto result = categoryService.findById(existsId);

        // Assert
        Assertions.assertNotNull(result);
        verify(categoryRepository, times(1)).findById(existsId);
    }

    @Test
    void findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExixts() {
        // Assert
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            categoryService.findById(nonExistsId);
        });

        verify(categoryRepository, times(1)).findById(nonExistsId);
    }

}