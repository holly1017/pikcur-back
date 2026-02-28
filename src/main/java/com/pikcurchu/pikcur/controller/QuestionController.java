package com.pikcurchu.pikcur.controller;

import com.pikcurchu.pikcur.dto.request.ReqAnswerDto;
import com.pikcurchu.pikcur.dto.request.ReqQuestionDto;
import com.pikcurchu.pikcur.dto.response.ResQuestionDetailDto;
import com.pikcurchu.pikcur.service.QuestionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Tag(name = "question api", description = "문의 관련 api")
@RequestMapping("/question")
@RequiredArgsConstructor
public class QuestionController {
    private final QuestionService questionService;

    @Operation(summary = "문의 등록", description = "문의 객체를 받아 등록")
    @PostMapping
    public void insertQuestion(@RequestPart("questionData") ReqQuestionDto questionDto,
            @RequestPart(required = false) MultipartFile image,
            HttpServletRequest request) {
        Integer memberNo = (Integer) request.getAttribute("memberNo");
        Integer questionId = questionService.insertQuestion(questionDto, memberNo);
        if (image != null && !image.isEmpty()) {
            questionService.insertQuestionImage(questionId, image);
        }
    }

    @Operation(summary = "답변 등록", description = "답변 객체를 받아 등록")
    @PostMapping("/answer/{questionId}")
    public void insertAnswer(@PathVariable Integer questionId,
            @RequestPart("answerData") ReqAnswerDto answerDto,
            @RequestPart(required = false) MultipartFile image,
            HttpServletRequest request) {
        Integer memberNo = (Integer) request.getAttribute("memberNo");
        Integer answerId = questionService.insertAnswer(questionId, answerDto, memberNo);
        if (image != null && !image.isEmpty()) {
            questionService.insertAnswerImage(answerId, image);
        }
    }

    @Operation(summary = "문의 상세 내역 조회", description = "문의 아이디를 통해 상세 내용을 조회")
    @GetMapping("/{questionId}")
    public ResQuestionDetailDto selectQuestionDetail(@PathVariable Integer questionId) {
        return questionService.selectQuestionDetail(questionId);
    }

}
