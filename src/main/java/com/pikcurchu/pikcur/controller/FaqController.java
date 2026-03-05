package com.pikcurchu.pikcur.controller;

import com.pikcurchu.pikcur.dto.response.ResFaqPageDto;
import com.pikcurchu.pikcur.service.FaqService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "FAQ api", description = "FAQ api")
@RequestMapping("/faq")
@RequiredArgsConstructor
public class FaqController {
    private final FaqService faqService;

    @Operation(summary = "FAQ 조회", description = "FAQ 조회")
    @GetMapping
    public ResFaqPageDto selectSearchGoodsList(@RequestParam int currentPage) {
        return faqService.selectFaq(currentPage);
    }
}
