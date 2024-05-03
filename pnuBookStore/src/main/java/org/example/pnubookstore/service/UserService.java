package org.example.pnubookstore.service;

import org.example.pnubookstore.dto.CreateUserDto;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
	void createUser(CreateUserDto createUserDto);
}
