package com.pikcurchu.pikcur.mapper;

import com.pikcurchu.pikcur.dto.request.ReqReviewDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReviewMapper {
    int insertReview(ReqReviewDto reqReviewDto);

    int insertReviewChoiceMap(Integer reviewId, List<Integer> choiceIds);
}
