package com.devsuperior.dscatalog.resources;

import com.devsuperior.dscatalog.dto.CategoryDto;
import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.services.CategoryService;
import com.devsuperior.dscatalog.services.exceptions.DataBasesException;
import com.devsuperior.dscatalog.services.exceptions.ResourceNotFoundException;
import com.devsuperior.dscatalog.tests.Factory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CategoryResource.class)
class CategoryResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

    @Autowired
    private ObjectMapper objectMapper;

    private PageImpl<CategoryDto> page;
    private Category category;
    private Long existsId;
    private Long nonExistsId;
    private Long dependentId;
    private CategoryDto categoryDto;


    @BeforeEach
    void setUp() {
        existsId = 1L;
        nonExistsId = 2L;
        dependentId = 3L;
        categoryDto = Factory.createCategoryDto();
        category = Factory.createCategory();
        page = new PageImpl<>(List.of(categoryDto));

        Mockito.when(categoryService.findAllPaged(Mockito.any())).thenReturn(page);

        Mockito.when(categoryService.findById(existsId)).thenReturn(categoryDto);
        Mockito.when(categoryService.findById(nonExistsId)).thenThrow(ResourceNotFoundException.class);

        Mockito.when(categoryService.create(categoryDto)).thenReturn(categoryDto);

        Mockito.when(categoryService.update(existsId, categoryDto)).thenReturn(categoryDto);
        Mockito.when(categoryService.update(nonExistsId, categoryDto)).thenThrow(ResourceNotFoundException.class);

        doNothing().when(categoryService).delete(existsId);
        Mockito.doThrow(ResourceNotFoundException.class).when(categoryService).delete(nonExistsId);
        Mockito.doThrow(DataBasesException.class).when(categoryService).delete(dependentId);

    }

    @Test
    void findAllShouldReturnPage() throws Exception {
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/categories")
                .accept(MediaType.APPLICATION_JSON));
        result.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void findByIdShouldReturnCategoryWhenIdExixts() throws Exception {
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/categories/{id}", existsId)
                .accept(MediaType.APPLICATION_JSON));
        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.id").exists());
        result.andExpect(jsonPath("$.name").exists());
    }

    @Test
    void findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExixts() throws Exception {
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/categories/{id}", nonExistsId)
                .accept(MediaType.APPLICATION_JSON));
        result.andExpect(status().isNotFound());
    }

    @Test
    void createShouldReturnCategoryDto() throws Exception {
        String jsonBody = objectMapper.writeValueAsString(categoryDto);

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/categories")
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));
        result.andExpect(status().isCreated());
    }

    @Test
    void updateShouldReturnCategoryDtoWhenIdExists() throws Exception {
        String jsonBody = objectMapper.writeValueAsString(categoryDto);

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.put("/categories/{id}", existsId)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
        result.andExpect(status().isOk());
    }

    @Test
    void updateShouldNotFoundWhenIdDoesNotExists() throws Exception {
        String jsonBody = objectMapper.writeValueAsString(categoryDto);

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.put("/categories/{id}", nonExistsId)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
        result.andExpect(status().isNotFound());
    }

    @Test
    void deleteShouldReturnNoContentWhenIdExists() throws Exception {
        ResultActions result = mockMvc.perform(delete("/categories/{id}", existsId));
        result.andExpect(status().isNoContent());
    }

    @Test
    void deleteShouldNotFoundWhenIdDoesNotExists() throws Exception {
        ResultActions result = mockMvc.perform(delete("/categories/{id}", nonExistsId));
        result.andExpect(status().isNotFound());
    }

    @Test
    void deleteShouldBadRequestWhenIdIsDependent() throws Exception {
        ResultActions result = mockMvc.perform(delete("/categories/{id}", dependentId));
        result.andExpect(status().isBadRequest());
    }
}