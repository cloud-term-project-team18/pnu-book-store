package org.example.pnubookstore.repository;

import java.util.Optional;

import org.example.pnubookstore.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<User, Long> {

	Optional<User> findByUsername(String username);
	boolean existsByUsername(String username);
}
