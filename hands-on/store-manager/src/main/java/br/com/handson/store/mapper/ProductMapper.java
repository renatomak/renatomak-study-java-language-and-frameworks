package br.com.handson.store.mapper;

import br.com.handson.store.dto.ProductDto;
import br.com.handson.store.model.Product;
import org.springframework.stereotype.Component;


@Component
public class ProductMapper {

    public static ProductDto toDto(Product entity) {
        ProductDto dto = new ProductDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setQuantity(entity.getQuantity());
        return dto;
    }

    public static Product toEntity(ProductDto dto) {
        Product entity = new Product();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setQuantity(dto.getQuantity());
        return entity;
    }
}
