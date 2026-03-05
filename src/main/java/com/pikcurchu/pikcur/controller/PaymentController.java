package com.pikcurchu.pikcur.controller;

import com.pikcurchu.pikcur.dto.request.ReqVerifyPaymentDto;
import com.pikcurchu.pikcur.dto.response.ResPaymentAddressDto;
import com.pikcurchu.pikcur.dto.response.ResVerifyPaymentDto;
import com.pikcurchu.pikcur.service.PaymentService;
// ... (다른 import)
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "payment api", description = "결제 관련 api")
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @Operation(summary = "결제 검증 요청", description = "결제 검증 로직")
    @PostMapping("/verify")
    public ResVerifyPaymentDto verifyPayment(@RequestBody ReqVerifyPaymentDto dto, HttpServletRequest request) {
        Integer memberNo = (Integer) request.getAttribute("memberNo");
        paymentService.verifyPayment(dto, memberNo);
        return new ResVerifyPaymentDto("success", "결제 검증 성공");
    }

    @Operation(summary = "낙찰 결제 전 정보 주소정보 조회", description = "낙찰 결제 전 정보 주소정보 조회 API")
    @GetMapping("/info")
    public ResPaymentAddressDto selectPaymentAddress(HttpServletRequest request,
            @RequestParam Integer bidId) {
        Integer memberNo = (Integer) request.getAttribute("memberNo");
        return paymentService.selectPaymentAddress(memberNo, bidId);
    }

    @Operation(summary = "즉시 결제 전 정보 주소정보 조회", description = "즉시 결제 전 정보 주소정보조회 API")
    @GetMapping("/buyout/{goodsId}")
    public ResPaymentAddressDto selectBuyoutPaymentAddress(HttpServletRequest request,
            @PathVariable Integer goodsId) {
        Integer memberNo = (Integer) request.getAttribute("memberNo");
        return paymentService.selectBuyoutPaymentAddress(memberNo, goodsId);
    }
}