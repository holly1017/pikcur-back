package com.pikcurchu.pikcur.service;

import com.pikcurchu.pikcur.common.ResponseCode;
import com.pikcurchu.pikcur.dto.request.ReqReviewDto;
import com.pikcurchu.pikcur.exception.BusinessException;
import com.pikcurchu.pikcur.mapper.ReviewMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewMapper reviewMapper;

    @Transactional
    public void insertReview(Integer storeId, ReqReviewDto reqReviewDto, Integer memberNo) {
        reqReviewDto.setStoreId(storeId);
        reqReviewDto.setMemberNo(memberNo);
        int result = reviewMapper.insertReview(reqReviewDto);
        if (result == 0) {
            throw new BusinessException(ResponseCode.INVALID_REQUEST);
        }
        Integer reviewId = reqReviewDto.getReviewId();

        if (reqReviewDto.getChoiceIds() != null && !reqReviewDto.getChoiceIds().isEmpty()) {
            int detailResult = reviewMapper.insertReviewChoiceMap(reviewId, reqReviewDto.getChoiceIds());
            if (detailResult == 0) {
                throw new BusinessException(ResponseCode.INTERNAL_SERVER_ERROR);
            }
        }
    }
}
