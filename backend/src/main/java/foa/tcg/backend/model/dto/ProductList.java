package foa.tcg.backend.model.dto;

import foa.tcg.backend.model.entity.Product;
import java.util.List;

public record ProductList(List<Product> products) {
}
