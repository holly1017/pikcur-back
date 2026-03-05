package com.pikcurchu.pikcur.controller;

import com.pikcurchu.pikcur.dto.request.ReqAccountDto;
import com.pikcurchu.pikcur.service.AccountService;
import com.pikcurchu.pikcur.vo.Account;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "account api", description = "계좌 API")
@RestController
@RequestMapping("/mypage")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @Operation(summary = "계좌 추가", description = "계좌 추가 API")
    @PostMapping("/account")
    public void insertAccount(@RequestBody ReqAccountDto req, HttpServletRequest request) {
        Integer memberNo = (Integer) request.getAttribute("memberNo");
        accountService.insertAccount(req, memberNo);
    }

    @Operation(summary = "계좌 조회", description = "계좌 조회 API")
    @GetMapping("/account")
    public List<Account> selectAccountList(HttpServletRequest request) {
        Integer memberNo = (Integer) request.getAttribute("memberNo");
        return accountService.selectAccountList(memberNo);
    }

    @Operation(summary = "계좌 수정", description = "계좌 수정 API")
    @PutMapping("/account")
    public void updateAccount(@RequestBody ReqAccountDto req, HttpServletRequest request) {
        Integer memberNo = (Integer) request.getAttribute("memberNo");
        accountService.updateAccount(req, memberNo);
    }

    @Operation(summary = "계좌 삭제", description = "계좌 삭제 API")
    @DeleteMapping("/account/{accountId}")
    public void deleteAccount(@PathVariable Integer accountId, HttpServletRequest request) {
        Integer memberNo = (Integer) request.getAttribute("memberNo");
        accountService.deleteAccount(accountId, memberNo);
    }

    @Operation(summary = "주 계좌 변경", description = "주 계좌 변경 API")
    @PutMapping("/account/{accountId}/set-default")
    public void updateDefaultAccount(@PathVariable Integer accountId, HttpServletRequest request) {
        Integer memberNo = (Integer) request.getAttribute("memberNo");
        accountService.updateDefaultAccount(accountId, memberNo);
    }
}
