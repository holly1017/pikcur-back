package com.pikcurchu.pikcur.controller;

import com.pikcurchu.pikcur.dto.request.ReqShippingDto;
import com.pikcurchu.pikcur.dto.response.ResTransactionDetailDto;
import com.pikcurchu.pikcur.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@Tag(name = "transaction api", description = "거래 관련 api")
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;
    @Value("${sweettracker.key}")
    private String sweetTrackerKey;

    @Operation(summary = "거래 상세내역 조회", description = "거래 아이디를 통한 거래 상세내역 조회")
    @GetMapping("/{transactionId}")
    public ResTransactionDetailDto selectTransactionInfo(@PathVariable Integer transactionId) {
        return transactionService.selectTransactionInfo(transactionId);
    }

    @Operation(summary = "거래 배송조회", description = "거래 아이디를 통한 상품 배송현황 조회")
    @GetMapping("/{transactionId}/shipping")
    public void selectShippingStatus(@RequestParam String code, @RequestParam String invoice,
            HttpServletResponse response) throws IOException {
        String url = "https://info.sweettracker.co.kr/tracking/5"
                + "?t_key=" + sweetTrackerKey
                + "&t_code=" + code
                + "&t_invoice=" + invoice;

        response.sendRedirect(url);
    }

    @Operation(summary = "거래 운송장 등록", description = "거래 아이디를 통한 상품 운송장 번호 등록")
    @PostMapping("/{transactionId}/shipping")
    public void insertShippingInfo(@PathVariable Integer transactionId, @RequestBody ReqShippingDto reqShippingDto) {
        transactionService.insertShippingInfo(transactionId, reqShippingDto);
    }

    @Operation(summary = "거래 구매확정 (상태 변경)", description = "거래 아이디를 통한 거래 구매확정 상태 변경")
    @PutMapping("/{transactionId}/confirm")
    public void confirmPurchase(@PathVariable Integer transactionId) {
        transactionService.confirmPurchase(transactionId);
    }
}
