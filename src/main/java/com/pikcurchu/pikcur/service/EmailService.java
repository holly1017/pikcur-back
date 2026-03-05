package com.pikcurchu.pikcur.service;

import com.pikcurchu.pikcur.common.ResponseCode;
import com.pikcurchu.pikcur.exception.BusinessException;
import com.pikcurchu.pikcur.mapper.EmailMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;
    private final StringRedisTemplate redisTemplate;
    private final EmailMapper emailMapper;

    private String createCode() {
        Random random = new Random();
        int code = random.nextInt(900000) + 100000;
        return String.valueOf(code);
    }

    public void sendVerificationCode(String email, boolean checkDuplicate) {
        if (checkDuplicate && selectEmail(email)) {
            throw new BusinessException(ResponseCode.DUPLICATE);
        }

        String code = createCode();

        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        ops.set("auth:" + email, code, 3, TimeUnit.MINUTES);
        try {
            jakarta.mail.internet.MimeMessage message = mailSender.createMimeMessage();
            org.springframework.mail.javamail.MimeMessageHelper helper = new org.springframework.mail.javamail.MimeMessageHelper(
                    message, true, "UTF-8");
            helper.setTo(email);
            helper.setSubject("Pikcur 이메일 인증번호");
            helper.setText("<p>인증번호는 <b>" + code + "</b> 입니다.</p>", true);
            mailSender.send(message);
        } catch (Exception e) {
            throw new BusinessException(ResponseCode.MAIL_SEND_FAIL);
        }
    }

    public void verifyCode(String email, String inputCode) {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        String savedCode = ops.get("auth:" + email);

        if (savedCode == null) {
            throw new BusinessException(ResponseCode.EXPIRATION);
        }

        if (!savedCode.equals(inputCode)) {
            throw new BusinessException(ResponseCode.INCONSISTENCY);
        }
    }

    public boolean selectEmail(String email) {
        return emailMapper.countByEmail(email) > 0;
    }
}
