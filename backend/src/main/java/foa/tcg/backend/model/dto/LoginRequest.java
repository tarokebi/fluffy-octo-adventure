package foa.tcg.backend.model.dto;

// DTO: ログインリクエストのモデル
public class LoginRequest {

    private String username;
    private String password;

    // ゲッターとセッター
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
