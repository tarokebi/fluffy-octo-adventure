package foa.tcg.backend.util.loader;

import com.fasterxml.jackson.databind.ObjectMapper;
import foa.tcg.backend.model.dto.ProductList;
import foa.tcg.backend.model.entity.Product;
import foa.tcg.backend.repository.CatalogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.asm.TypeReference;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component
public class ProductJsonDataLoaderUtil implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(ProductJsonDataLoaderUtil.class);
    private final CatalogRepository catalogRepository;
    private final JdbcClient jdbcClient;
    private final ObjectMapper objectMapper;

    public ProductJsonDataLoaderUtil(CatalogRepository catalogRepository, JdbcClient jdbcClient, ObjectMapper objectMapper) {
        this.catalogRepository = catalogRepository;
        this.jdbcClient = jdbcClient;
        this.objectMapper = objectMapper;
    }

    @Override
    public void run(String... args) {
        if (this.count() == 0) {
            try (InputStream inputStream = TypeReference.class.getResourceAsStream("/data/products.json")) {
                ProductList allProducts = objectMapper.readValue(inputStream, ProductList.class);
                log.info("--- Reading {} products from JSON data and saving to in-memory collection... ---", allProducts.products().size());
                this.saveAll(allProducts.products());
            } catch (IOException e) {
                throw new RuntimeException("Error reading products.json", e);
            }
        } else {
            log.info("--- Not loading products from JSON data because collection contains data. ---");
        }
    }

    public int count() {
        return jdbcClient.sql("SELECT * FROM product")
                .query()
                .listOfRows()
                .size();
    }

    public void saveAll(List<Product> productList) {
        for (Product product : productList) {
            catalogRepository.create(product);
        }
    }
}
