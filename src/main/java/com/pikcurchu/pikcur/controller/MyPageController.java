package com.pikcurchu.pikcur.controller;

import com.pikcurchu.pikcur.dto.response.MyPageInfoDto;
import com.pikcurchu.pikcur.dto.response.ResMyStoreDto;
import com.pikcurchu.pikcur.service.MyPageService;
import com.pikcurchu.pikcur.vo.Member;
import com.pikcurchu.pikcur.vo.Store;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "mypage api", description = "마이페이지 관련 API")
@RestController
@RequestMapping("/mypage")
@RequiredArgsConstructor
public class MyPageController {
    private final MyPageService myPageService;

    @Operation(summary = "내정보 조회", description = "내 정보 조회 API")
    @GetMapping("/profile")
    public MyPageInfoDto selectMyInfoById(HttpServletRequest request) {
        Integer memberNo = (Integer) request.getAttribute("memberNo");
        return myPageService.selectMyInfoById(memberNo);
    }

    @Operation(summary = "내정보 수정", description = "내 정보 수정 API")
    @PutMapping("/profile")
    public void updateMyInfo(@RequestBody Member member, HttpServletRequest request) {
        Integer memberNo = (Integer) request.getAttribute("memberNo");
        member.setMemberNo(memberNo);
        myPageService.updateMyInfo(member);
    }

    @Operation(summary = "내상점 조회", description = "내 상점 조회 API")
    @GetMapping("/store")
    public ResMyStoreDto selectMyStore(HttpServletRequest request) {
        Integer memberNo = (Integer) request.getAttribute("memberNo");
        return myPageService.selectMyStore(memberNo);
    }

    @Operation(summary = "내상점 수정", description = "내 상점 수정 API")
    @PutMapping("/store")
    public void updateMyStore(@RequestPart("store") Store store,
            @RequestPart(required = false) MultipartFile image) {
        myPageService.updateMyStore(store);
        if (image != null && !image.isEmpty()) {
            myPageService.updateStoreProfile(store.getStoreId(), image);
        }
    }

    @Operation(summary = "비밀번호 변경", description = "비밀번호 변경 API")
    @PostMapping("/password")
    public void updatePassword(@RequestBody Member member, HttpServletRequest request) {
        Integer memberNo = (Integer) request.getAttribute("memberNo");
        myPageService.updatePassword(memberNo, member.getPassword());
    }

}
