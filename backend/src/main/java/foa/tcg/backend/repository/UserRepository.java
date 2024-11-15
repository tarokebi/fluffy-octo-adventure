package foa.tcg.backend.repository;

import foa.tcg.backend.model.entity.User;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static foa.tcg.backend.util.validator.ValidatorUtil.validateUser;

@Repository
public class UserRepository {

	private final JdbcClient jdbcClient;

	public UserRepository(JdbcClient jdbcClient) {
		this.jdbcClient = jdbcClient;
	}

	public List<User> getAll() {
		return jdbcClient.sql("SELECT * FROM appuser")
				.query(User.class)
				.list();
	}

	public Optional<User> getById(int id) {
		return jdbcClient.sql("SELECT * FROM appuser WHERE id = :id")
				.param("id", id)
				.query(User.class)
				.optional();
	}

	public Optional<User> getByEmail(String email) {
		return jdbcClient.sql("SELECT * FROM appuser WHERE email = :email")
				.param("email", email)
				.query(User.class)
				.optional();
	}

	public int create(User user) {
		validateUser(user);
		return jdbcClient.sql("INSERT INTO appuser(id, name, email, created_at, updated_at) VALUES(?,?,?,?,?)")
				.params(List.of(user.id(), user.name(), user.email(), LocalDateTime.now(), LocalDateTime.now()))
				.update();
	}

	public Optional<User> update(User user, Integer id) {
		validateUser(user);
		var ifUpdated = jdbcClient.sql("UPDATE appuser SET name = ?, email = ?, updated_at = ? WHERE id = ?")
				.params(List.of(user.name(), user.email(), LocalDateTime.now(), id))
				.update();
		Assert.state(ifUpdated == 1, "Failed to update user '" + user.name() + "'");
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
