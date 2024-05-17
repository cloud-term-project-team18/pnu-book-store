package org.example.pnubookstore.domain.user.service;

import org.example.pnubookstore.domain.user.dto.CreateUserDto;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
	void createUser(CreateUserDto createUserDto);
	void emailVerify(String email);
	void afterEmailForm();
	void login();
}
