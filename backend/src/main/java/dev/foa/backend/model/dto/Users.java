package dev.foa.backend.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
 @NoArgsConstructor
 @AllArgsConstructor
public class UserDTO {
    private Long id;

    @NotBlank(message = "'name' is required.")
    @Size(min = 1, max = 100, message = "'name' must be 1-100 characters.")
    private String name;

    @NotBlank(message = "email' is required.")
    @Email(message = "You need valid email.")
    private String email;

//    private ArrayList<User> users;
//
//    public UserDTO() {
//        this.users = new ArrayList<User>();
//    }
//
//    public User get(int i) {
//        return this.users.get(i);
//    }
//
//    public void add(User user) {
//        this.users.add(user);
//    }
//
//    public int size() {
//        return this.users.size();
//    }
}
