package com.pikcurchu.pikcur.controller;

import com.pikcurchu.pikcur.dto.request.ReqSigninDto;
import com.pikcurchu.pikcur.dto.request.ReqSignupDto;
import com.pikcurchu.pikcur.dto.response.ResSigninDto;
import com.pikcurchu.pikcur.service.AuthService;
import com.pikcurchu.pikcur.vo.Member;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "auth api", description = "인증 관련 API")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @Operation(summary = "로그인", description = "JWT 토큰 방식 로그인")
    @PostMapping("/members/signin")
    public ResSigninDto signin(@Valid @RequestBody ReqSigninDto request) {
        return authService.signin(request);
    }

    @Operation(summary = "회원가입", description = "회원가입 API")
    @PostMapping("/members/signup")
    public Integer signup(@Valid @RequestBody ReqSignupDto request) {
        return authService.signup(request);
    }

    @Operation(summary = "아이디 조회", description = "아이디 조회 API")
    @PostMapping("/members/find-id")
    public String findIdByEmail(@RequestBody Member member) {
        return authService.findIdByEmail(member.getEmail());
    }

    @Operation(summary = "계정 삭제", description = "계정 삭제 API")
    @PutMapping("/members/delete-account")
    public Boolean updateMemberToWithdrawal(HttpServletRequest request) {
        Integer memberNo = (Integer) request.getAttribute("memberNo");
        return authService.updateMemberToWithdrawal(memberNo);
    }

    @Operation(summary = "아이디 중복", description = "아이지 중복 체크 API")
    @PostMapping("/members/duplicate-id")
    public Integer selectId(@RequestBody Member member) {
        return authService.countById(member.getId());
    }

    @Operation(summary = "로그인 상태에서 비밀번호 변경", description = "로그인 상태에서 비밀번호 변경 API")
    @PostMapping("/members/password-status-login")
    public Integer updatePasswordStatusLogin(@RequestBody Member member, HttpServletRequest request) {
        Integer memberNo = (Integer) request.getAttribute("memberNo");
        return authService.updatePasswordStatusLogin(memberNo, member.getPassword());
    }

    @Operation(summary = "비로그인 상태에서 비밀번호 변경", description = "비로그인 상태에서 비밀번호 변경 API")
    @PostMapping("/members/password-status-unLogin")
    public Integer updatePasswordStatusUnLogin(@RequestBody Member member) {
        return authService.updatePasswordStatusUnLogin(member.getId(), member.getPassword());
    }
}
