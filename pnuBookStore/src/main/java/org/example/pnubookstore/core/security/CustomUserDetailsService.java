package org.example.pnubookstore.core.security;

import org.example.pnubookstore.core.exception.BaseExceptionStatus;
import org.example.pnubookstore.repository.UserJpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {
	private final UserJpaRepository userJpaRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return new CustomUserDetails(userJpaRepository.findByUsername(username)
			.orElseThrow(() -> new UsernameNotFoundException(BaseExceptionStatus.USER_NOT_FOUND.getErrorMessage())));
	}
}
