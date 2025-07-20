package foa.tcg.backend.model.dto;

import jakarta.validation.constraints.NotBlank;

import lombok.Data;

// DTO: ログインリクエストのモデル
@Data
public class LoginRequest {

	private String username;
	@NotBlank(message = "Email address is required.")
	private String email;
	@NotBlank(message = "Password is required.")
	private String password;


	public String getEmail(String email) {
		return email;
	}

	public String getPassword() {
		return this.password;
	}

	// キミは完璧で究極のゲッターとセッター
	public String getUsername() {
		return this.username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	// add email getter
}
