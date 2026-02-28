package com.pikcurchu.pikcur.mapper;

import com.pikcurchu.pikcur.dto.request.ReqAnswerDto;
import com.pikcurchu.pikcur.dto.request.ReqQuestionDto;
import com.pikcurchu.pikcur.dto.response.ResQuestionDetailDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface QuestionMapper {
    int insertQuestion(ReqQuestionDto questionDto);

    int insertAnswer(ReqAnswerDto answerDto);

    ResQuestionDetailDto findQuestionDetailById(Integer questionId);

    int updateQuestionImagePath(Integer questionId, String uploadedPath);

    int updateAnswerImagePath(Integer answerId, String uploadedPath);
}
