package com.pikcurchu.pikcur.service;

import com.pikcurchu.pikcur.common.ResponseCode;
import com.pikcurchu.pikcur.dto.request.ReqAnswerDto;
import com.pikcurchu.pikcur.dto.request.ReqQuestionDto;
import com.pikcurchu.pikcur.dto.response.ResQuestionDetailDto;
import com.pikcurchu.pikcur.exception.BusinessException;
import com.pikcurchu.pikcur.mapper.ImageMapper;
import com.pikcurchu.pikcur.mapper.QuestionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionMapper questionMapper;
    private final ImageMapper imageMapper;
    private final FileService fileService;

    @Transactional
    public Integer insertQuestion(ReqQuestionDto questionDto, Integer memberNo) {
        questionDto.setMemberNo(memberNo);
        int result = questionMapper.insertQuestion(questionDto);
        if (result == 0)
            throw new BusinessException(ResponseCode.INVALID_REQUEST);

        return questionDto.getQuestionId();
    }

    @Transactional
    public Integer insertAnswer(Integer questionId, ReqAnswerDto answerDto, Integer memberNo) {
        answerDto.setMemberNo(memberNo);
        answerDto.setQuestionId(questionId);
        int result = questionMapper.insertAnswer(answerDto);
        if (result == 0)
            throw new BusinessException(ResponseCode.INVALID_REQUEST);

        return answerDto.getAnswerId();
    }

    public ResQuestionDetailDto selectQuestionDetail(Integer questionId) {
        ResQuestionDetailDto detail = questionMapper.findQuestionDetailById(questionId);
        if (detail == null)
            throw new BusinessException(ResponseCode.NOT_FOUND);
        return detail;
    }

    @Transactional
    public void insertQuestionImage(Integer questionId, MultipartFile image) {
        String uploadedPath = fileService.questionUploadFile(image);
        int result = questionMapper.updateQuestionImagePath(questionId, uploadedPath);
        if (result == 0)
            throw new BusinessException(ResponseCode.NOT_FOUND);
    }

    @Transactional
    public void insertAnswerImage(Integer answerId, MultipartFile image) {
        String uploadedPath = fileService.answerUploadFile(image);
        int result = questionMapper.updateAnswerImagePath(answerId, uploadedPath);
        if (result == 0)
            throw new BusinessException(ResponseCode.NOT_FOUND);
    }
}
