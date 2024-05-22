package org.example.pnubookstore.domain.user.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import org.example.pnubookstore.core.exception.Exception404;
import org.example.pnubookstore.domain.user.entity.EmailVerification;
import org.example.pnubookstore.domain.user.repository.UserEmailVerificationRedisRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Service
@RequiredArgsConstructor
public class UserEmailVerificationService {

    private final JavaMailSender javaMailSender;
    private final UserEmailVerificationRedisRepository userEmailVerificationRedisRepository;



    public EmailVerification isUserEmailValid(String uuid){
        // 파라미터로 넘겨준 정보를 레디스에서 실제로 있는 유저인지 검색
        return userEmailVerificationRedisRepository.findByUuid(uuid);
    }

    public void sendVerifyEmail(String email, String uuid){
        String subject = "인증 메일";
        String content = "메일 테스트 내용";
        content += "<a href=\"http://localhost:8080/signUp/after-email?uuid=" + uuid + "\">링크 클릭</a>";
        String from = "pnuauction@gmail.com";
        String to = email;

        MimeMessage mail = javaMailSender.createMimeMessage();
        MimeMessageHelper mailHelper = new MimeMessageHelper(mail,"UTF-8");
        try{
            mailHelper.setFrom(from);
            // 빈에 아이디 설정한 것은 단순히 smtp 인증을 받기 위해 사용 따라서 보내는이(setFrom())반드시 필요
            // 보내는이와 메일주소를 수신하는이가 볼때 모두 표기 되게 원하신다면 아래의 코드를 사용하시면 됩니다.
            //mailHelper.setFrom("보내는이 이름 <보내는이 아이디@도메인주소>");
            mailHelper.setTo(to);
            mailHelper.setSubject(subject);
            mailHelper.setText(content, true);
            // true는 html을 사용하겠다는 의미입니다.

            // 비동기로 메일 보내기
            CompletableFuture<Void> voidCompletableFuture = CompletableFuture.runAsync(() -> {
                javaMailSender.send(mail);
            });

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }
}
