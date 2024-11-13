package foa.tcg.backend.service;

import foa.tcg.backend.exception.UserAlreadyExistsException;
import foa.tcg.backend.exception.UserNotFoundException;
import foa.tcg.backend.model.entity.User;
import foa.tcg.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.getAll();
    }

    public User getUserById(Integer id) {
        Optional<User> user = userRepository.getById(id);

        if (user.isEmpty()) {
            throw new UserNotFoundException("UserId '"+ id +"' was not found.");
        }

        return user.get();
    }

    public void createUser(User user) {
        if (userRepository.existsById(user.id())) {
            throw new UserAlreadyExistsException("UserId '"+ user.id() +"' is already in use.");
        }
        if (userRepository.existsByEmail(user.email())) {
            throw new UserAlreadyExistsException("Email '"+ user.email() +"' is already in use.");
        }

        userRepository.create(user);
    }

    public void updateUser(User user, Integer id) {
        User existingUser = getUserById(id);

        if (!user.email().equals(existingUser.email()) && userRepository.existsByEmail(user.email())) {
            throw new UserAlreadyExistsException("Email '"+ user.email() +"' is already in use.");
        }
        if (user.name().equals(existingUser.name())) {
            if (user.email().equals(existingUser.email())) {
                throw new UserAlreadyExistsException("Change name or email.");
            }
        }

        userRepository.update(user, id);
    }

    public void deleteUser(Integer id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("UserId '"+ id +"' was not found.");
        }

        userRepository.delete(id);
    }

}
