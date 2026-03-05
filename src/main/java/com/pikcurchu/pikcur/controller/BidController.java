package com.pikcurchu.pikcur.controller;

import com.pikcurchu.pikcur.dto.request.ReqBidDto;
import com.pikcurchu.pikcur.dto.response.ResGoodsBidsPageDto;
import com.pikcurchu.pikcur.service.BidService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "bid api", description = "입찰 관련 api")
@RequestMapping("/bid")
@RequiredArgsConstructor
public class BidController {
    private final BidService bidService;

    @Operation(summary = "입찰 리스트 조회", description = "상품 아이디를 통한 입찰 리스트 조회")
    @GetMapping("/{goodsId}/list")
    public ResGoodsBidsPageDto selectBidList(@PathVariable Integer goodsId, @RequestParam int currentPage) {
        return bidService.selectBidList(goodsId, currentPage);
    }

    @Operation(summary = "입찰 등록", description = "상품에 대한 입찰 등록")
    @PostMapping("/{goodsId}")
    public void insertBid(@PathVariable Integer goodsId, @RequestBody ReqBidDto reqBidDto, HttpServletRequest request) {
        Integer memberNo = (Integer) request.getAttribute("memberNo");
        bidService.insertBid(goodsId, reqBidDto, memberNo);
    }

}
