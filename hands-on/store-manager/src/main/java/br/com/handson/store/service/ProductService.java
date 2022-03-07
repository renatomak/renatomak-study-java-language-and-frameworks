package br.com.handson.store.service;

import br.com.handson.store.dto.ProductDto;
import br.com.handson.store.mapper.ProductMapper;
import br.com.handson.store.model.Product;
import br.com.handson.store.repository.ProductRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Service
@RequiredArgsConstructor
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public ProductDto createProduct(ProductDto productDto) {
        Product product = convertToModel(productDto);
        Integer id = productRepository.save(product).getId();
        productDto.setId(id);

        return productDto;
    }

    public List<ProductDto> getAll() {
        return productRepository.findAll()
                .stream().map(ProductMapper::toDto)
                .collect(Collectors.toList());
    }

    public Product convertToModel(ProductDto productsDto) {
        Product product = new Product();

        if(!(productsDto.getName() == null)) {
            product.setName(productsDto.getName());
        }
        if(!(productsDto.getQuantity() == null)) {
            product.setQuantity(productsDto.getQuantity());
        }

        return product;
    }
}
