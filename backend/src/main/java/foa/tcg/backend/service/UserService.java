package foa.tcg.backend.service;

import foa.tcg.backend.exception.UserAlreadyExistsException;
import foa.tcg.backend.exception.UserNotFoundException;
import foa.tcg.backend.model.dto.UserDto;
import foa.tcg.backend.model.entity.User;
import foa.tcg.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static foa.tcg.backend.util.converter.UserConverterUtil.convertUserDtoToUser;
import static foa.tcg.backend.util.converter.UserConverterUtil.convertUserToUserDto;

@Service
public class UserService {

	private final UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public List<UserDto> getAllUsers() {
		List<User> userList = userRepository.getAll();
		List<UserDto> userDtoList = new ArrayList<>(List.of());
		for (User user : userList) {
			userDtoList.add(convertUserToUserDto(user));
		}
		return userDtoList;
	}

	public UserDto getUserById(Integer id) {
		Optional<User> userOptional = userRepository.getById(id);
		if (userOptional.isEmpty()) {
			throw new UserNotFoundException("UserId '" + id + "' was not found.");
		} else {
			return convertUserToUserDto(userOptional.get());
		}
	}

	public void createUser(UserDto userDto) {
		if (userRepository.existsById(userDto.getId())) {
			throw new UserAlreadyExistsException("UserId '" + userDto.getId() + "' is already in use.");
		}
		if (userRepository.existsByEmail(userDto.getEmail())) {
			throw new UserAlreadyExistsException("Email '" + userDto.getEmail() + "' is already in use.");
		}
		userRepository.create(convertUserDtoToUser(userDto));
	}

	public void updateUser(UserDto userDto, Integer id) {
		UserDto existingUser = getUserById(id);
		if (!userDto.getEmail().equals(existingUser.getEmail()) && userRepository.existsByEmail(userDto.getEmail())) {
			throw new UserAlreadyExistsException("Email '" + userDto.getEmail() + "' is already in use.");
		}
		if (userDto.getName().equals(existingUser.getName()) && userDto.getEmail().equals(existingUser.getEmail())) {
			throw new UserAlreadyExistsException("Change name or email.");
		}
		userDto.setId(id);
		userRepository.update(convertUserDtoToUser(userDto), id);
	}

	public void deleteUser(Integer id) {
		if (!userRepository.existsById(id)) {
			throw new UserNotFoundException("UserId '" + id + "' was not found.");
		}
		userRepository.delete(id);
	}

}
