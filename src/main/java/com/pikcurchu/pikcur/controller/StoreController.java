package com.pikcurchu.pikcur.controller;

import com.pikcurchu.pikcur.dto.response.*;
import com.pikcurchu.pikcur.service.StoreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "store api", description = "상점 관련 api")
@RestController
@RequestMapping("/store")
@RequiredArgsConstructor
public class StoreController {
    private final StoreService storeService;

    @Operation(summary = "상점 정보 조회", description = "상점 아이디를 통해 상점 정보를 조회")
    @GetMapping("/{storeId}")
    public ResStoreDetailDto selectStoreInfo(@PathVariable Integer storeId, HttpServletRequest request) {
        Integer memberNo = (Integer) request.getAttribute("memberNo");
        return storeService.selectStoreInfo(storeId, memberNo);
    }

    @Operation(summary = "로그인 회원의 상점 정보 조회", description = "회원 번호를 통해 상점 정보를 조회")
    @GetMapping("/my-store")
    public ResStoreDetailDto selectMyStoreInfo(HttpServletRequest request) {
        Integer memberNo = (Integer) request.getAttribute("memberNo");
        return storeService.selectMyStoreInfo(memberNo);
    }

    @Operation(summary = "리뷰 리스트 조회", description = "상점 아이디를 통해 상점 리뷰 리스트를 조회")
    @GetMapping("/reviews/{storeId}")
    public ResReviewPageDto selectStoreReviews(@PathVariable Integer storeId, @RequestParam int currentPage) {
        return storeService.selectStoreReview(storeId, currentPage);
    }

    @Operation(summary = "상품 리스트 조회", description = "상점 아이디를 통해 상품 리스트를 조회")
    @GetMapping("/goods/{storeId}")
    public ResGoodsPageDto selectStoreGoods(@PathVariable Integer storeId, HttpServletRequest request,
            @RequestParam int currentPage) {
        Integer memberNo = (Integer) request.getAttribute("memberNo");
        return storeService.selectStoreGoods(storeId, memberNo, currentPage);
    }

    @Operation(summary = "판매한 거래 리스트 조회", description = "판매한 거래 리스트를 조회")
    @GetMapping("/{storeId}/transactions/sell")
    public ResSellTransactionPageDto selectStoreSellTransaction(@PathVariable Integer storeId,
            @RequestParam int currentPage) {
        return storeService.selectStoreSellTransaction(storeId, currentPage);
    }

    @Operation(summary = "구매한 거래 리스트 조회", description = "구매한 거래 리스트를 조회")
    @GetMapping("/{storeId}/transactions/buy")
    public ResBuyTransactionPageDto selectStoreBuyTransaction(@PathVariable Integer storeId,
            @RequestParam int currentPage) {
        return storeService.selectStoreBuyTransaction(storeId, currentPage);
    }

    @Operation(summary = "입찰 내역 리스트 조회", description = "입찰한 리스트를 조회")
    @GetMapping("/{storeId}/bids")
    public ResBidsPageDto selectStoreBids(@PathVariable Integer storeId, @RequestParam int currentPage) {
        return storeService.selectStoreBids(storeId, currentPage);
    }

    @Operation(summary = "낙찰 내역 리스트 조회", description = "낙찰한 리스트를 조회")
    @GetMapping("/{storeId}/win-bids")
    public ResBidsPageDto selectStoreWinBids(@PathVariable Integer storeId, @RequestParam int currentPage) {
        return storeService.selectStoreWinBids(storeId, currentPage);
    }

    @Operation(summary = "찜 상품 리스트 조회", description = "찜한 상품 리스트를 조회")
    @GetMapping("/{storeId}/goods-likes")
    public ResGoodsPageDto selectGoodsLike(@PathVariable Integer storeId, @RequestParam int currentPage) {
        return storeService.selectGoodsLike(storeId, currentPage);
    }

    @Operation(summary = "찜 브랜드 리스트 조회", description = "찜한 브랜드 리스트를 조회")
    @GetMapping("/{storeId}/brand-likes")
    public ResBrandLikePageDto selectBrandsLike(@PathVariable Integer storeId, @RequestParam int currentPage) {
        return storeService.selectBrandsLike(storeId, currentPage);
    }

    @Operation(summary = "팔로우 리스트 조회", description = "팔로우한 상점 리스트를 조회")
    @GetMapping("/{storeId}/follows")
    public ResFollowPageDto selectFollow(@PathVariable Integer storeId, @RequestParam int currentPage) {
        return storeService.selectFollow(storeId, currentPage);
    }

    @Operation(summary = "받은 문의 리스트 조회", description = "상점 번호를 통해 받은 상품관련 문의 리스트를 조회")
    @GetMapping("/{storeId}/questions/received")
    public ResQuestionPageDto selectReceivedQuestions(@PathVariable Integer storeId, @RequestParam int currentPage) {
        return storeService.selectReceivedQuestions(storeId, currentPage);
    }

    @Operation(summary = "보낸 문의 리스트 조회", description = "상점 번호를 통해 보낸 상품관련 문의 리스트를 조회")
    @GetMapping("/{storeId}/questions/sent")
    public ResQuestionPageDto selectSentQuestions(@PathVariable Integer storeId, @RequestParam int currentPage) {
        return storeService.selectSentQuestions(storeId, currentPage);
    }

    @Operation(summary = "상점 신고", description = "상점 번호를 통해 해당 상점 신고")
    @PostMapping("/report/{storeId}")
    public void reportStore(@PathVariable Integer storeId, HttpServletRequest request) {
        Integer memberNo = (Integer) request.getAttribute("memberNo");
        storeService.reportStore(storeId, memberNo);
    }

    @Operation(summary = "상점 차단", description = "상점 번호를 통해 해당 상점 차단")
    @PostMapping("/block/{storeId}")
    public void reportBlock(@PathVariable Integer storeId, HttpServletRequest request) {
        Integer memberNo = (Integer) request.getAttribute("memberNo");
        storeService.blockStore(storeId, memberNo);
    }

    @Operation(summary = "팔로우", description = "팔로우")
    @PostMapping("/follow/{storeId}")
    public void insertFollow(@PathVariable Integer storeId, HttpServletRequest request) {
        Integer memberNo = (Integer) request.getAttribute("memberNo");
        storeService.insertFollow(storeId, memberNo);
    }

    @Operation(summary = "팔로우 삭제", description = "팔로우 삭제")
    @DeleteMapping("/follow/{storeId}")
    public void deleteFollow(@PathVariable Integer storeId, HttpServletRequest request) {
        Integer memberNo = (Integer) request.getAttribute("memberNo");
        storeService.deleteFollow(storeId, memberNo);
    }
}
