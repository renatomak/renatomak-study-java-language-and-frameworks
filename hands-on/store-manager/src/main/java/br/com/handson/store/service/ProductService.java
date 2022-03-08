package br.com.handson.store.service;

import br.com.handson.store.dto.ProductDto;
import br.com.handson.store.exception.EntityInUseException;
import br.com.handson.store.exception.EntityNotFoundException;
import br.com.handson.store.mapper.ProductMapper;
import br.com.handson.store.model.Product;
import br.com.handson.store.repository.ProductRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static br.com.handson.store.mapper.ProductMapper.*;

@Data
@Service
@RequiredArgsConstructor
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public ProductDto createProduct(ProductDto productDto) {
        Product product = toEntity(productDto);
        Long id = productRepository.save(product).getId();
        productDto.setId(id);
        return productDto;
    }

    public List<ProductDto> getAll() {
        return productRepository.findAll()
                .stream().map(ProductMapper::toDto)
                .collect(Collectors.toList());
    }

    public ProductDto updateProduct(Long id, ProductDto productDto) {
        Product product = productRepository.getById(id);
        if (!(productDto.getName() == null)) {
            product.setName(productDto.getName());
        }
        if (!(productDto.getQuantity() == null)) {
            product.setQuantity(productDto.getQuantity());
        }
        return toDto(productRepository.save(product));
    }

    public void remove(Long id) {
        try {
            productRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException(String.format("Produto com o códido %d não encontrado", id));
        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(String.format("Produto com o códido %d não pode ser removido, pois está em uso", id));
        }

    }

}
