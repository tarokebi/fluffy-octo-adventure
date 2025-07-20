package foa.tcg.backend.model.entity;

import java.time.LocalDateTime;

//import jakarta.persistence.Id;
//import jakarta.persistence.Table;
//import jakarta.persistence.UniqueConstraint;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"id", "email"})})
@Entity
public record User
(
		@Id
			@GeneratedValue(strategy = GenerationType.IDENTITY)
		Integer id,

		@NotBlank(message = "[Entity Validation] Name is required.")

		@Size(min = 1, max = 250, message = "[Entity Validation] Name must be 1-250 characters.")
		String name,

		@NotBlank(message = "[Entity Validation] Email address is required.")
		@Email(message = "[Entity Validation] Email address seems to be invalid.")
		String email,

			String password,

			@Enumerated(EnumType.STRING) Role role,

		@CreatedDate
		LocalDateTime createdAt,

		@LastModifiedDate
		LocalDateTime updatedAt
		) {

		public enum Role {
			USER, ADMIN
		}
}
