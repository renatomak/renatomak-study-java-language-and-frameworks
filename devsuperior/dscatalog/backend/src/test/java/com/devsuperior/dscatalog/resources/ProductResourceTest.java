package com.devsuperior.dscatalog.resources;

import com.devsuperior.dscatalog.dto.ProductDto;
import com.devsuperior.dscatalog.services.ProductService;
import com.devsuperior.dscatalog.services.exceptions.DataBasesException;
import com.devsuperior.dscatalog.services.exceptions.ResourceNotFoundException;
import com.devsuperior.dscatalog.tests.Factory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductResource.class)
class ProductResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    private PageImpl<ProductDto> page;
    private ProductDto productDto;
    private long existingId;
    private long nonExistingId;
    private long depndentId;

    @BeforeEach
    void setUp() throws Exception{
        productDto = Factory.createProductDto();
        page = new PageImpl<>(List.of(productDto));
        existingId = 1L;
        nonExistingId = 2L;
        depndentId = 3;

        when(productService.findAllPaged(any())).thenReturn(page);

        when(productService.findById(existingId)).thenReturn(productDto);
        when(productService.findById(nonExistingId)).thenThrow(ResourceNotFoundException.class);

        when(productService.update(eq(existingId), any())).thenReturn(productDto);
        when(productService.update(eq(nonExistingId), any())).thenThrow(ResourceNotFoundException.class);

        doNothing().when(productService).delete(existingId);
        doThrow(ResourceNotFoundException.class).when(productService).delete(nonExistingId);
        doThrow(DataBasesException.class).when(productService).delete(depndentId);

        when(productService.create(productDto)).thenReturn(productDto);
    }

    @Test
    public void deleteShouldReturnNoContentWhenIdExists() throws Exception  {
        ResultActions result = mockMvc.perform(delete("/products/{id}", existingId));
        result.andExpect(status().isNoContent());
    }

    @Test
    public void deleteShouldNotFoundWhenIdDoesNotExists() throws Exception  {
        ResultActions result = mockMvc.perform(delete("/products/{id}", nonExistingId));
        result.andExpect(status().isNotFound());
    }

    @Test
    public void createShouldreturnProductDto() throws Exception {
        String jsonbody = objectMapper.writeValueAsString(productDto);

        ResultActions result = mockMvc.perform(post("/products")
                .content(jsonbody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
        result.andExpect(status().isCreated());
    }

    @Test
    public void updateShouldReturnProductDtoWhenIdExists() throws Exception {
        String jsonbody = objectMapper.writeValueAsString(productDto);

        ResultActions result = mockMvc.perform(put("/products/{id}", existingId)
                        .content(jsonbody)
                        .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.id").exists());
        result.andExpect(jsonPath("$.name").exists());
        result.andExpect(jsonPath("$.description").exists());
    }

    @Test
    public void updateShouldNotFoundWhenIdDoesNotExists() throws Exception {
        String jsonbody = objectMapper.writeValueAsString(productDto);

        ResultActions result = mockMvc.perform(put("/products/{id}", nonExistingId)
                .content(jsonbody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
        result.andExpect(status().isNotFound());
    }

    @Test
    public void findAllShouldReturnPage() throws Exception {

        ResultActions result = mockMvc.perform(get("/products")
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
    }

    @Test
    public void findByIdShouldReturnProductWhenIdExixts()  throws Exception {
        ResultActions result = mockMvc.perform(get("/products/{id}", existingId)
                .accept(MediaType.APPLICATION_JSON));
        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.id").exists());
        result.andExpect(jsonPath("$.name").exists());
        result.andExpect(jsonPath("$.description").exists());
    }

    @Test
    public void findByIdShouldNotFindWhenIdDoesNotExixts()  throws Exception {
        ResultActions result = mockMvc.perform(get("/products/{id}", nonExistingId)
                .accept(MediaType.APPLICATION_JSON));
        result.andExpect(status().isNotFound());
    }

}