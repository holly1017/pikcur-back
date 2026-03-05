package com.pikcurchu.pikcur.controller;

import com.pikcurchu.pikcur.dto.response.ResBlockDto;
import com.pikcurchu.pikcur.service.BlockService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "block api", description = "차단상점 API")
@RestController
@RequestMapping("/mypage")
@RequiredArgsConstructor
public class BlockController {
    private final BlockService blockService;

    @Operation(summary = "차단상점 조회", description = "차단 상점 조회 API")
    @GetMapping("/blocked-stores")
    public List<ResBlockDto> selectBlockList(HttpServletRequest request) {
        Integer memberNo = (Integer) request.getAttribute("memberNo");
        return blockService.selectBlockList(memberNo);
    }

    @Operation(summary = "차단상점 삭제", description = "차단 상점 삭제 API")
    @DeleteMapping("/blocked-stores/{blockId}")
    public void deleteBlockStore(@PathVariable Integer blockId, HttpServletRequest request) {
        Integer memberNo = (Integer) request.getAttribute("memberNo");
        blockService.deleteBlockStore(blockId, memberNo);
    }
}
