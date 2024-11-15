package foa.tcg.backend.repository;

import foa.tcg.backend.model.dto.UserDto;
import foa.tcg.backend.model.entity.User;
import foa.tcg.backend.util.validator.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {

	private static final Logger log = LoggerFactory.getLogger(UserRepository.class);
	private final JdbcClient jdbcClient;

	public UserRepository(JdbcClient jdbcClient) {
		this.jdbcClient = jdbcClient;
	}

	public List<UserDto> getAll() {
		List<User> rawUserList = jdbcClient.sql("SELECT * FROM appuser")
				.query(User.class)
				.list();
		List<UserDto> userDtoList = new ArrayList<UserDto>(List.of());
		for (int i = 0; i < rawUserList.size(); i++) {
			userDtoList.add(new UserDto(
					rawUserList.get(i).id(),
					rawUserList.get(i).name(),
					rawUserList.get(i).email(),
					rawUserList.get(i).createdAt(),
					rawUserList.get(i).updatedAt()
			));
		}
		return userDtoList;
	}

	public Optional<UserDto> getById(int id) {
		var rawUser = jdbcClient.sql("SELECT * FROM appuser WHERE id = :id")
				.param("id", id)
				.query(User.class)
				.optional();
		if (rawUser.isPresent()) {
			return Optional.of(new UserDto(
					rawUser.get().id(),
					rawUser.get().name(),
					rawUser.get().email(),
					rawUser.get().createdAt(),
					rawUser.get().updatedAt()
			));
		} else {
			return Optional.empty();
		}
	}

	public Optional<UserDto> getByEmail(String email) {
		Optional<User> rawUser = jdbcClient.sql("SELECT * FROM appuser WHERE email = :email")
				.param("email", email)
				.query(User.class)
				.optional();
		if (rawUser.isPresent()) {
			return Optional.of(new UserDto(
					rawUser.get().id(),
					rawUser.get().name(),
					rawUser.get().email(),
					rawUser.get().createdAt(),
					rawUser.get().updatedAt()
			));
		} else {
			return Optional.empty();
		}
	}

	public int create(UserDto userDto) {
		Validator.validateUserDto(userDto);
		User rawUser = new User(
				userDto.id(),
				userDto.name(),
				userDto.email(),
				userDto.createdAt(),
				userDto.updatedAt()
		);
		Validator.validateUser(rawUser);
		return jdbcClient.sql("INSERT INTO appuser(id, name, email, created_at, updated_at) VALUES(?,?,?,?,?)")
				.params(List.of(rawUser.id(), rawUser.name(), rawUser.email(), LocalDateTime.now(), LocalDateTime.now()))
				.update();
	}

	public Optional<UserDto> update(UserDto userDto, Integer id) {
		Validator.validateUserDto(userDto);
		User rawUser = new User(
				userDto.id(),
				userDto.name(),
				userDto.email(),
				userDto.createdAt(),
				userDto.updatedAt()
		);
		Validator.validateUser(rawUser);
		var ifUpdated = jdbcClient.sql("UPDATE appuser SET name = ?, email = ?, updated_at = ? WHERE id = ?")
				.params(List.of(rawUser.name(), rawUser.email(), LocalDateTime.now(), id))
				.update();
		Assert.state(ifUpdated == 1, "Failed to update user '" + userDto.name() + "'");
		return this.getById(id);
	}

	public int delete(Integer id) {
		return jdbcClient.sql("DELETE FROM appuser WHERE id = :id")
				.param("id", id)
				.update();
	}

	public boolean existsById(Integer id) {
		return this.getById(id).isPresent();
	}

	public boolean existsByEmail(String email) {
		return this.getByEmail(email).isPresent();
	}

}
