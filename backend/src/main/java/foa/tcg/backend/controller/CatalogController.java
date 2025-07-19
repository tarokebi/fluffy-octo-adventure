package foa.tcg.backend.controller;

import foa.tcg.backend.model.dto.ProductDto;
import foa.tcg.backend.service.CatalogService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/catalog")
public class CatalogController {

    private final CatalogService catalogService;

    public CatalogController(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @GetMapping("")
    public List<ProductDto> getAllProducts() {
        return catalogService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ProductDto getProductById(@PathVariable Integer id) {
        return catalogService.getProductById(id);
    }
}
