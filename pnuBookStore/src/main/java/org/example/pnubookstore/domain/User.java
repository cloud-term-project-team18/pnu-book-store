package org.example.pnubookstore.domain;

import java.util.Objects;

import org.example.pnubookstore.domain.constant.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "user_tb")
public class User extends AuditingEntity{

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true)
	private String username;
	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	@Enumerated(value = EnumType.STRING)
	private Role role;

	@Builder
	public User(Long id, String username, String password, Role role) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.role = role;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		User user = (User)o;
		return Objects.equals(getId(), user.getId()) && Objects.equals(getUsername(),
			user.getUsername());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId(), getUsername());
	}
}
