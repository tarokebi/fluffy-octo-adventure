package foa.tcg.backend.controller;

import foa.tcg.backend.model.entity.User;
import foa.tcg.backend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping({"", "/"})
    List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping({"/{id}", "/{id}/"})
    User getUserById(@PathVariable Integer id) {
        return userService.getUserById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping({"", "/"})
    void createUser(@Valid @RequestBody User user) {
        userService.createUser(user);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping({"/{id}", "/{id}/"})
    void updateUser(@Valid @RequestBody User user, @PathVariable Integer id) {
        userService.updateUser(user, id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping({"/{id}", "/{id}/"})
    void deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
    }

}
