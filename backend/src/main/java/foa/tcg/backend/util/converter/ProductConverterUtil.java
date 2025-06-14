package foa.tcg.backend.util.converter;

import foa.tcg.backend.model.dto.ProductDto;
import foa.tcg.backend.model.entity.Product;
import foa.tcg.backend.util.validator.ValidatorUtil;

import java.util.Optional;

public class ProductConverterUtil {

    public static ProductDto convertProductToProductDto(Product product) {
        ValidatorUtil.validateProduct(product);
        return new ProductDto(
                product.id(),
                product.name(),
                product.price()
        );
    }

    public static ProductDto convertProductToProductDto(Optional<Product> productOptional) {
        return productOptional.map(ProductConverterUtil::convertProductToProductDto).orElse(null);
    }

    public static Product convertProductDtoToProduct(ProductDto productDto) {
        ValidatorUtil.validateProductDto(productDto);
        return new Product(
                productDto.getId(),
                productDto.getName(),
                productDto.getPrice()
        );
    }

    public static Product convertProductDtoToProduct(Optional<ProductDto> productDtoOptional) {
        return productDtoOptional.map(ProductConverterUtil::convertProductDtoToProduct).orElse(null);
    }
}
