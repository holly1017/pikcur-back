package com.pikcurchu.pikcur.controller;

import com.pikcurchu.pikcur.dto.request.ReqReviewDto;
import com.pikcurchu.pikcur.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "review api", description = "리뷰 관련 api")
@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @Operation(summary = "리뷰 등록", description = "상점에 대한 리뷰 등록")
    @PostMapping("/{storeId}")
    public void insertReview(@PathVariable Integer storeId, @RequestBody ReqReviewDto reqReviewDto,
            HttpServletRequest request) {
        Integer memberNo = (Integer) request.getAttribute("memberNo");
        reviewService.insertReview(storeId, reqReviewDto, memberNo);
    }
}
