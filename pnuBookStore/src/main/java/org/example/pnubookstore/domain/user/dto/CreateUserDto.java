package org.example.pnubookstore.domain.user.dto;

public record CreateUserDto(
	String email,
	String password
) {
}
