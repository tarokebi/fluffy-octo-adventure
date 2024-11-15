package foa.tcg.backend.util.converter;

import foa.tcg.backend.model.dto.UserDto;
import foa.tcg.backend.model.entity.User;
import foa.tcg.backend.util.validator.ValidatorUtil;

import java.util.Optional;

public class UserConverterUtil {

	public static UserDto convertUserToUserDto(User user) {
		ValidatorUtil.validateUser(user);
		return new UserDto(
				user.id(),
				user.name(),
				user.email(),
				user.createdAt(),
				user.updatedAt()
		);
	}

	public static UserDto convertUserToUserDto(Optional<User> userOptional) {
		return userOptional.map(UserConverterUtil::convertUserToUserDto).orElse(null);
	}

	public static User convertUserDtoToUser(UserDto userDto) {
		ValidatorUtil.validateUserDto(userDto);
		return new User(
				userDto.getId(),
				userDto.getName(),
				userDto.getEmail(),
				userDto.getCreatedAt(),
				userDto.getUpdatedAt()
		);
	}

	public static User convertUserDtoToUser(Optional<UserDto> userDtoOptional) {
		return userDtoOptional.map(UserConverterUtil::convertUserDtoToUser).orElse(null);
	}

}
