package foa.tcg.backend.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "product")
public record Product(
        @Id Integer id,
        String name,
        Integer price
) {
}
