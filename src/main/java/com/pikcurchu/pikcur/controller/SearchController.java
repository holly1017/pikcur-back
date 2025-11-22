package com.pikcurchu.pikcur.controller;

import com.pikcurchu.pikcur.dto.response.ResGoodsPageDto;
import com.pikcurchu.pikcur.dto.response.ResSearchDto;
import com.pikcurchu.pikcur.service.SearchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name="search api", description = "검색 관련 api")
@RequestMapping("/search")
@RequiredArgsConstructor
public class SearchController {
    private final SearchService searchService;

    @Operation(summary = "검색어 키워드 조회", description = "키워드를 통한 상품 리스트 조회")
    @GetMapping
    public ResponseEntity<ResGoodsPageDto> selectSearchGoodsList(@RequestParam String keyword, HttpServletRequest request, @RequestParam int currentPage) {
        Integer memberNo = (Integer) request.getAttribute("memberNo");
        ResGoodsPageDto searchList = searchService.selectSearchGoodsList(keyword, memberNo, currentPage);
        return new ResponseEntity<>(searchList, HttpStatus.OK);
    }

    @Operation(summary = "검색어 히스토리 조회", description = "회원 번호를 통한 최근 검색어 히스토리 리스트 조회")
    @GetMapping("/recent")
    public ResponseEntity<List<ResSearchDto>> selectSearchHistory(HttpServletRequest request) {
        Integer memberNo = (Integer) request.getAttribute("memberNo");
        List<ResSearchDto> historyList = searchService.selectSearchHistory(memberNo);
        return new ResponseEntity<>(historyList, HttpStatus.OK);
    }

    @Operation(summary = "인기 검색어 조회", description = "인기 검색어 top10 리스트 조회")
    @GetMapping("/popular")
    public ResponseEntity<List<ResSearchDto>> selectSearchTop10() {
        List<ResSearchDto> top10List = searchService.selectSearchTop10();
        return new ResponseEntity<>(top10List, HttpStatus.OK);
    }

    @Operation(summary = "검색어 히스토리 삭제", description = "최근 검색어 삭제")
    @DeleteMapping("/recent/{searchHistoryId}")
    public ResponseEntity<Integer> deleteSearchHistory(HttpServletRequest request, @PathVariable Integer searchHistoryId) {
        Integer memberNo = (Integer) request.getAttribute("memberNo");
        Integer response = searchService.deleteSearchHistory(memberNo, searchHistoryId);
        return new ResponseEntity<Integer>(response, HttpStatus.OK);
    }
}
