package com.pikcurchu.pikcur.controller;

import com.pikcurchu.pikcur.dto.request.ReqEmailDto;
import com.pikcurchu.pikcur.service.EmailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "email api", description = "이메일 관련 api")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class EmailController {
    private final EmailService emailService;

    @Operation(summary = "회원가입용 인증번호 요청 API", description = "회원가입 시 인증번호 발송 요청 (중복체크)")
    @PostMapping("/email/send-code-for-signup")
    public void sendCodeForSignup(@RequestBody ReqEmailDto reqEmailDto) {
        emailService.sendVerificationCode(reqEmailDto.getEmail(), true);
    }

    @Operation(summary = "인증용 인증번호 요청 API", description = "인증 시 인증번호 발송 요청")
    @PostMapping("/email/send-code-for-find")
    public void sendCodeForFind(@RequestBody ReqEmailDto reqEmailDto) {
        emailService.sendVerificationCode(reqEmailDto.getEmail(), false);
    }

    @Operation(summary = "인증번호 검증 API", description = "이메일과 입력한 인증번호 검증")
    @PostMapping("/email/verify-code")
    public void verifyCode(@RequestBody ReqEmailDto reqEmailDto) {
        emailService.verifyCode(reqEmailDto.getEmail(), reqEmailDto.getCode());
    }
}
