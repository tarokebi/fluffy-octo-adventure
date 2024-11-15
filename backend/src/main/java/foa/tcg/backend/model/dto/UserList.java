package foa.tcg.backend.model.dto;

import foa.tcg.backend.model.entity.User;

import java.util.List;

public record UserList(
		List<User> users
) {

}
