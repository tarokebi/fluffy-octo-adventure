package foa.tcg.backend.model.dto;

import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserDto {

    @Id
    Integer id;
    @NotBlank(message = "Name is required.")
    @Size(min = 1, max = 250, message = "Name must be 1-250 characters.")
    String name;
    @NotBlank(message = "Email address is required.")
    @Email(message = "Email address seems to be invalid.")
    String email;
    @CreatedDate
    LocalDateTime createdAt;
    @LastModifiedDate
    LocalDateTime updatedAt;

    public UserDto(
        Integer id,
        @NotBlank(message = "[Entity Validation] Name is required.")
        @Size(min = 1, max = 250, message = "[Entity Validation] Name must be 1-250 characters.")
        String name,
        @NotBlank(message = "[Entity Validation] Email address is required.")
        @Email(message = "[Entity Validation] Email address seems to be invalid.")
        String email,
        LocalDateTime createdAt,
        LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

}
