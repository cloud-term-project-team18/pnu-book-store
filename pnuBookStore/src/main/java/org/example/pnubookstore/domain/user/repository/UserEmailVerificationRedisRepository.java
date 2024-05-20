package org.example.pnubookstore.domain.user.repository;

import org.example.pnubookstore.domain.user.entity.EmailVerification;
import org.springframework.data.repository.CrudRepository;

public interface UserEmailVerificationRedisRepository extends CrudRepository<EmailVerification, Long> {

}
