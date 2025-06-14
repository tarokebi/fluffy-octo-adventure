package foa.tcg.backend.model.dto;

import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDto {
    @Id
    Integer id;
    @NotBlank(message = "Name is required.")
    String name;
    @NotNull(message = "Price is required.")
    @Min(value = 0, message = "Price must be positive.")
    Integer price;

    public ProductDto(Integer id, String name, Integer price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }
}
