package foa.tcg.backend.repository;

import foa.tcg.backend.model.entity.Product;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CatalogRepository {

    private final JdbcClient jdbcClient;

    public CatalogRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<Product> getAll() {
        return jdbcClient.sql("SELECT * FROM product")
                .query(Product.class)
                .list();
    }

    public Optional<Product> getById(int id) {
        return jdbcClient.sql("SELECT * FROM product WHERE id = :id")
                .param("id", id)
                .query(Product.class)
                .optional();
    }

    public int create(Product product) {
        return jdbcClient.sql("INSERT INTO product(id, name, price) VALUES(?,?,?)")
                .params(List.of(product.id(), product.name(), product.price()))
                .update();
    }
}
