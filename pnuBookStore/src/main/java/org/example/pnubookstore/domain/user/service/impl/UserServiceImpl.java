package org.example.pnubookstore.domain.user.service.impl;

import org.example.pnubookstore.core.exception.BaseExceptionStatus;
import org.example.pnubookstore.core.exception.Exception400;
import org.example.pnubookstore.domain.user.UserExceptionStatus;
import org.example.pnubookstore.domain.user.dto.CreateUserDto;
import org.example.pnubookstore.domain.user.repository.UserJpaRepository;
import org.example.pnubookstore.domain.user.service.UserService;
import org.example.pnubookstore.domain.user.entity.User;
import org.example.pnubookstore.domain.base.constant.Role;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
	private final UserJpaRepository userJpaRepository;
	private final PasswordEncoder passwordEncoder;

	@Transactional
	public void createUser(CreateUserDto userDto) {
		if (userJpaRepository.existsByEmail(userDto.email())){
			throw new Exception400(UserExceptionStatus.USERNAME_ALREADY_USED);
		}

		userJpaRepository.save(User.builder()
			.email(userDto.email())
			.password(passwordEncoder.encode(userDto.password()))
			.role(Role.ROLE_USER)
			.build());
	}
}
