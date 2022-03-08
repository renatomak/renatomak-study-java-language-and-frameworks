package br.com.handson.store.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ProductDto {

    @EqualsAndHashCode.Include
    private Long id;

    @NotNull(message = "The 'name' field is mandatory.")
    @NotEmpty(message = "O campo 'name' não pode ser vazio")
    @Size(min = 5, message
            = "'name' length must be at least 5")
    private String name;

    @Positive(message = "'quantity' must be larger than or equal to 1")
    @NotNull(message = "The 'quantity' field is mandatory.")
    private Integer quantity;

    public ProductDto setId(Long id) {
        this.id = id;
        return this;
    }

    public static class Builder {
        ProductDto productDto = new ProductDto();

        public ProductDto.Builder withName(String name) {
            productDto.name = name;
            return this;
        }

        public ProductDto.Builder withQuantity(Integer quantity) {
            productDto.quantity = quantity;
            return this;
        }

        public ProductDto build() { return productDto; }
    }

}

