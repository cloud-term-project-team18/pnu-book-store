package org.example.pnubookstore.domain.user.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@RedisHash(value="email", timeToLive = 3600)
public class EmailVerification {

    @Id
    private String uuid;
    private long userId;

    public EmailVerification(String uuid, long userId) {
        this.uuid = uuid;
        this.userId = userId;
    }
}
