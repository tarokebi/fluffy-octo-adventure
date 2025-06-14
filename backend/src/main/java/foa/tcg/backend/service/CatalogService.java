package foa.tcg.backend.service;

import foa.tcg.backend.exception.ProductNotFoundException;
import foa.tcg.backend.model.dto.ProductDto;
import foa.tcg.backend.model.entity.Product;
import foa.tcg.backend.repository.CatalogRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static foa.tcg.backend.util.converter.ProductConverterUtil.convertProductDtoToProduct;
import static foa.tcg.backend.util.converter.ProductConverterUtil.convertProductToProductDto;

@Service
public class CatalogService {

    private final CatalogRepository catalogRepository;

    public CatalogService(CatalogRepository catalogRepository) {
        this.catalogRepository = catalogRepository;
    }

    public List<ProductDto> getAllProducts() {
        List<Product> productList = catalogRepository.getAll();
        List<ProductDto> productDtoList = new ArrayList<>(List.of());
        for (Product product : productList) {
            productDtoList.add(convertProductToProductDto(product));
        }
        return productDtoList;
    }

    public ProductDto getProductById(Integer id) {
        Optional<Product> productOptional = catalogRepository.getById(id);
        if (productOptional.isEmpty()) {
            throw new ProductNotFoundException("ProductId '" + id + "' was not found.");
        }
        return convertProductToProductDto(productOptional.get());
    }

    public void createProduct(ProductDto productDto) {
        catalogRepository.create(convertProductDtoToProduct(productDto));
    }
}
