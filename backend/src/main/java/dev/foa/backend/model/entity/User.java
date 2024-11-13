package dev.foa.backend.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
public record User(
        @Id
        Integer id,

        @NotBlank(message = "Name is required.")
        @Size(min = 1, max = 250, message = "Name must be 1-250 characters.")
        String name,

        @NotBlank(message = "Email address is required.")
        @Email(message = "Email address seems to be invalid.")
        String email,

        @CreatedDate
        LocalDateTime createdAt,

        @LastModifiedDate
        LocalDateTime updatedAt
) {}
