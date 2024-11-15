package foa.tcg.backend.service;

import foa.tcg.backend.exception.UserAlreadyExistsException;
import foa.tcg.backend.exception.UserNotFoundException;
import foa.tcg.backend.model.dto.UserDto;
import foa.tcg.backend.repository.UserRepository;
import foa.tcg.backend.util.validator.Validator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

	private final UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public List<UserDto> getAllUsers() {
		return userRepository.getAll();
	}

	public UserDto getUserById(Integer id) {
		Optional<UserDto> userDto = userRepository.getById(id);
		if (userDto.isEmpty()) {
			throw new UserNotFoundException("UserId '" + id + "' was not found.");
		}
		return userDto.get();
	}

	public void createUser(UserDto userDto) {
		Validator.validateUserDto(userDto);
		if (userRepository.existsById(userDto.id())) {
			throw new UserAlreadyExistsException("UserId '" + userDto.id() + "' is already in use.");
		}
		if (userRepository.existsByEmail(userDto.email())) {
			throw new UserAlreadyExistsException("Email '" + userDto.email() + "' is already in use.");
		}
		userRepository.create(userDto);
	}

	public void updateUser(UserDto userDto, Integer id) {
		Validator.validateUserDto(userDto);
		UserDto existingUser = getUserById(id);
		if (!userDto.email().equals(existingUser.email()) && userRepository.existsByEmail(userDto.email())) {
			throw new UserAlreadyExistsException("Email '" + userDto.email() + "' is already in use.");
		}
		if (userDto.name().equals(existingUser.name())) {
			if (userDto.email().equals(existingUser.email())) {
				throw new UserAlreadyExistsException("Change name or email.");
			}
		}
		userRepository.update(userDto, id);
	}

	public void deleteUser(Integer id) {
		if (!userRepository.existsById(id)) {
			throw new UserNotFoundException("UserId '" + id + "' was not found.");
		}
		userRepository.delete(id);
	}

}
