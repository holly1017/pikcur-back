package com.pikcurchu.pikcur.controller;

import com.pikcurchu.pikcur.common.ResponseCode;
import com.pikcurchu.pikcur.dto.request.ReqGoodsDto;
import com.pikcurchu.pikcur.dto.response.*;
import com.pikcurchu.pikcur.exception.BusinessException;
import com.pikcurchu.pikcur.service.GoodsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "goods api", description = "상품 관련 api")
@RestController
@RequestMapping("/goods")
@RequiredArgsConstructor
public class GoodsController {
    private final GoodsService goodsService;

    @Operation(summary = "인기 상품 조회", description = "좋아요 수 기준 인기 상품 리스트 조회")
    @GetMapping("/popular")
    public List<ResGoodsItemDto> selectPopularGoodsList(HttpServletRequest request) {
        Integer memberNo = (Integer) request.getAttribute("memberNo");
        return goodsService.selectPopularGoodsList(memberNo);
    }

    @Operation(summary = "최근 본 상품 조회", description = "최근 본 상품 리스트 조회")
    @GetMapping("/recent")
    public List<ResGoodsItemDto> selectRecentViewGoodsList(HttpServletRequest request) {
        Integer memberNo = (Integer) request.getAttribute("memberNo");
        return goodsService.selectRecentViewGoodsList(memberNo);
    }

    @Operation(summary = "마감 임박 상품 조회", description = "마감 임박 상품 리스트 조회")
    @GetMapping("/closing-soon")
    public List<ResGoodsItemDto> selectGoodsListByEndDate(HttpServletRequest request) {
        Integer memberNo = (Integer) request.getAttribute("memberNo");
        return goodsService.selectGoodsListByEndDate(memberNo);
    }

    @Operation(summary = "카테고리 리스트 조회", description = "카테고리 리스트 조회")
    @GetMapping("/categories")
    public List<ResCategoryDto> selectCategories() {
        return goodsService.selectCategories();
    }

    @Operation(summary = "카테고리 별 상품 조회", description = "카테고리 아이디를 통한 상품 리스트 조회")
    @GetMapping("/categories/{categoryId}")
    public ResGoodsPageDto selectGoodsListByCategoryId(@PathVariable Integer categoryId, HttpServletRequest request,
            @RequestParam int currentPage) {
        Integer memberNo = (Integer) request.getAttribute("memberNo");
        return goodsService.selectGoodsListByCategoryId(categoryId, memberNo, currentPage);
    }

    @Operation(summary = "상품 상세 조회", description = "상품 아이디를 통한 상품 상세 조회")
    @GetMapping("/{goodsId}")
    public ResGoodsDetailDto selectGoodsDetailById(@PathVariable Integer goodsId, HttpServletRequest request) {
        Integer memberNo = (Integer) request.getAttribute("memberNo");
        return goodsService.selectGoodsDetailById(goodsId, memberNo);
    }

    @Operation(summary = "상품 문의 리스트 조회", description = "상품 아이디를 통한 상품 문의 리스트 조회")
    @GetMapping("/{goodsId}/questions")
    public ResGoodsQuestionsPageDto selectGoodsQuestionsById(@PathVariable Integer goodsId,
            @RequestParam int currentPage) {
        return goodsService.selectGoodsQuestionsById(goodsId, currentPage);
    }

    @Operation(summary = "상품 신고", description = "상품 번호를 통해 해당 상품 신고")
    @PostMapping("/report/{goodsId}")
    public void reportGoods(@PathVariable Integer goodsId, HttpServletRequest request) {
        Integer memberNo = (Integer) request.getAttribute("memberNo");
        goodsService.reportGoods(goodsId, memberNo);
    }

    @Operation(summary = "상품 찜", description = "상품 번호를 통해 해당 상품 찜")
    @PostMapping("/like/{goodsId}")
    public void goodsLike(@PathVariable Integer goodsId, HttpServletRequest request) {
        Integer memberNo = (Integer) request.getAttribute("memberNo");
        goodsService.insertGoodsLike(goodsId, memberNo);
    }

    @Operation(summary = "상품 찜 취소", description = "상품 번호를 통해 해당 상품 찜 취소")
    @DeleteMapping("/like/{goodsId}")
    public void deleteGoodsLike(@PathVariable Integer goodsId, HttpServletRequest request) {
        Integer memberNo = (Integer) request.getAttribute("memberNo");
        goodsService.deleteGoodsLike(goodsId, memberNo);
    }

    @Operation(summary = "상품 등록", description = "경매 상품 등록")
    @PostMapping
    public void insertGoods(@RequestPart("goodsData") ReqGoodsDto reqGoodsDto,
            @RequestPart("image") List<MultipartFile> images, HttpServletRequest request) {

        Integer memberNo = (Integer) request.getAttribute("memberNo");
        Integer goodsId = goodsService.insertGoods(reqGoodsDto, memberNo);
        if (images == null || images.isEmpty()) {
            throw new BusinessException(ResponseCode.REQUIRED_IMAGE);
        }
        goodsService.insertGoodsImages(goodsId, images);
    }
}
