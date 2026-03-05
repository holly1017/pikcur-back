package com.pikcurchu.pikcur.service;

import com.pikcurchu.pikcur.common.ResponseCode;
import com.pikcurchu.pikcur.dto.response.*;
import com.pikcurchu.pikcur.exception.BusinessException;
import com.pikcurchu.pikcur.mapper.StoreMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StoreService {

    // TODO: 공통 로직 분리하기
    private final StoreMapper storeMapper;
    private static final int PAGE_SIZE_6 = 6;
    private static final int PAGE_SIZE_21 = 21;

    public ResStoreDetailDto selectStoreInfo(Integer storeId, Integer memberNo) {
        ResStoreDetailDto storeDto = storeMapper.findStoreInfoById(storeId, memberNo);

        if (storeDto == null) {
            throw new IllegalArgumentException("Invalid store ID: " + storeId);
        }

        return storeDto;
    }

    public ResReviewPageDto selectStoreReview(Integer storeId, int currentPage) {
        int offset = (currentPage - 1) * PAGE_SIZE_6;

        // 2. 맵퍼에 파라미터 전달
        Map<String, Object> params = new HashMap<>();
        params.put("storeId", storeId);
        params.put("limit", PAGE_SIZE_6);
        params.put("offset", offset);

        // 3. 쿼리 2개 호출
        List<ResReviewItemDto> reviewList = storeMapper.findStoreReviewById(params);
        ResStoreAverageDto storeAverRating = storeMapper.findStoreReviewRatingAverage(storeId);
        int totalCount = storeMapper.countReviewsByStoreId(storeId);

        // 4. 총 페이지 수 계산
        int totalPages = (int) Math.ceil((double) totalCount / PAGE_SIZE_6);

        // 5. 결과를 DTO에 담아 반환 (React가 필요한 모든 정보)
        return new ResReviewPageDto(reviewList, storeAverRating, totalPages, totalCount);
    }

    public ResGoodsPageDto selectStoreGoods(Integer storeId, Integer memberNo, int currentPage) {
        int offset = (currentPage - 1) * PAGE_SIZE_21;

        // 2. 맵퍼에 파라미터 전달
        Map<String, Object> params = new HashMap<>();
        params.put("storeId", storeId);
        params.put("memberNo", memberNo);
        params.put("limit", PAGE_SIZE_21);
        params.put("offset", offset);

        // 3. 쿼리 2개 호출
        List<ResGoodsItemDto> goodsList = storeMapper.findStoreGoodsById(params);
        int totalCount = storeMapper.countStoreGoodsByStoreId(storeId);

        // 4. 총 페이지 수 계산
        int totalPages = (int) Math.ceil((double) totalCount / PAGE_SIZE_21);

        // 5. 결과를 DTO에 담아 반환 (React가 필요한 모든 정보)
        return new ResGoodsPageDto(goodsList, totalPages, totalCount);
    }

    public ResSellTransactionPageDto selectStoreSellTransaction(Integer storeId, int currentPage) {
        int offset = (currentPage - 1) * PAGE_SIZE_6;

        // 2. 맵퍼에 파라미터 전달
        Map<String, Object> params = new HashMap<>();
        params.put("storeId", storeId);
        params.put("limit", PAGE_SIZE_6);
        params.put("offset", offset);

        // 3. 쿼리 2개 호출
        List<ResTransactionItemDto> transList = storeMapper.findStoreSellTranactionById(params);
        int totalCount = storeMapper.countSellTransactionByStoreId(storeId);

        // 4. 총 페이지 수 계산
        int totalPages = (int) Math.ceil((double) totalCount / PAGE_SIZE_6);

        // 5. 결과를 DTO에 담아 반환 (React가 필요한 모든 정보)
        return new ResSellTransactionPageDto(transList, totalPages, totalCount);
    }

    public ResBuyTransactionPageDto selectStoreBuyTransaction(Integer storeId, int currentPage) {
        int offset = (currentPage - 1) * PAGE_SIZE_6;

        // 2. 맵퍼에 파라미터 전달
        Map<String, Object> params = new HashMap<>();
        params.put("storeId", storeId);
        params.put("limit", PAGE_SIZE_6);
        params.put("offset", offset);

        // 3. 쿼리 2개 호출
        List<ResTransactionItemDto> transList = storeMapper.findStoreBuyTranactionById(params);
        int totalCount = storeMapper.countBuyTransactionByStoreId(storeId);

        // 4. 총 페이지 수 계산
        int totalPages = (int) Math.ceil((double) totalCount / PAGE_SIZE_6);

        // 5. 결과를 DTO에 담아 반환 (React가 필요한 모든 정보)
        return new ResBuyTransactionPageDto(transList, totalPages, totalCount);
    }

    public ResBidsPageDto selectStoreBids(Integer storeId, int currentPage) {
        int offset = (currentPage - 1) * PAGE_SIZE_6;

        // 2. 맵퍼에 파라미터 전달
        Map<String, Object> params = new HashMap<>();
        params.put("storeId", storeId);
        params.put("limit", PAGE_SIZE_6);
        params.put("offset", offset);

        // 3. 쿼리 2개 호출
        List<ResBidItemDto> bidList = storeMapper.findBidById(params);
        int totalCount = storeMapper.countBidsByStoreId(storeId);

        // 4. 총 페이지 수 계산
        int totalPages = (int) Math.ceil((double) totalCount / PAGE_SIZE_6);

        // 5. 결과를 DTO에 담아 반환 (React가 필요한 모든 정보)
        return new ResBidsPageDto(bidList, totalPages, totalCount);
    }

    public ResBidsPageDto selectStoreWinBids(Integer storeId, int currentPage) {
        int offset = (currentPage - 1) * PAGE_SIZE_6;

        // 2. 맵퍼에 파라미터 전달
        Map<String, Object> params = new HashMap<>();
        params.put("storeId", storeId);
        params.put("limit", PAGE_SIZE_6);
        params.put("offset", offset);

        // 3. 쿼리 2개 호출
        List<ResBidItemDto> winBidList = storeMapper.findWinBidById(params);
        int totalCount = storeMapper.countWinBidsByStoreId(storeId);

        // 4. 총 페이지 수 계산
        int totalPages = (int) Math.ceil((double) totalCount / PAGE_SIZE_6);

        // 5. 결과를 DTO에 담아 반환 (React가 필요한 모든 정보)
        return new ResBidsPageDto(winBidList, totalPages, totalCount);
    }

    public ResGoodsPageDto selectGoodsLike(Integer storeId, int currentPage) {
        int offset = (currentPage - 1) * PAGE_SIZE_21;

        // 2. 맵퍼에 파라미터 전달
        Map<String, Object> params = new HashMap<>();
        params.put("storeId", storeId);
        params.put("limit", PAGE_SIZE_21);
        params.put("offset", offset);

        // 3. 쿼리 2개 호출
        List<ResGoodsItemDto> goodsList = storeMapper.findGoodsLikeById(params);
        int totalCount = storeMapper.countGoodsLikeGoodsByStoreId(storeId);

        // 4. 총 페이지 수 계산
        int totalPages = (int) Math.ceil((double) totalCount / PAGE_SIZE_21);

        // 5. 결과를 DTO에 담아 반환 (React가 필요한 모든 정보)
        return new ResGoodsPageDto(goodsList, totalPages, totalCount);
    }

    public ResBrandLikePageDto selectBrandsLike(Integer storeId, int currentPage) {
        int offset = (currentPage - 1) * PAGE_SIZE_6;

        // 2. 맵퍼에 파라미터 전달
        Map<String, Object> params = new HashMap<>();
        params.put("storeId", storeId);
        params.put("limit", PAGE_SIZE_6);
        params.put("offset", offset);

        // 3. 쿼리 2개 호출
        List<ResBrandItemDto> brandLikeList = storeMapper.findBrandLikeById(params);
        int totalCount = storeMapper.countBrandLikeByStoreId(storeId);

        // 4. 총 페이지 수 계산
        int totalPages = (int) Math.ceil((double) totalCount / PAGE_SIZE_6);

        // 5. 결과를 DTO에 담아 반환 (React가 필요한 모든 정보)
        return new ResBrandLikePageDto(brandLikeList, totalPages, totalCount);
    }

    public ResFollowPageDto selectFollow(Integer storeId, int currentPage) {
        int offset = (currentPage - 1) * PAGE_SIZE_6;

        // 2. 맵퍼에 파라미터 전달
        Map<String, Object> params = new HashMap<>();
        params.put("storeId", storeId);
        params.put("limit", PAGE_SIZE_6);
        params.put("offset", offset);

        // 3. 쿼리 2개 호출
        List<ResFollowItemDto> followList = storeMapper.findFollowById(params);
        int totalCount = storeMapper.countFollowByStoreId(storeId);

        // 4. 총 페이지 수 계산
        int totalPages = (int) Math.ceil((double) totalCount / PAGE_SIZE_6);

        // 5. 결과를 DTO에 담아 반환 (React가 필요한 모든 정보)
        return new ResFollowPageDto(followList, totalPages, totalCount);
    }

    public ResQuestionPageDto selectReceivedQuestions(Integer storeId, int currentPage) {
        // 1. offset 계산
        int offset = (currentPage - 1) * PAGE_SIZE_6;

        // 2. 맵퍼에 파라미터 전달
        Map<String, Object> params = new HashMap<>();
        params.put("storeId", storeId);
        params.put("limit", PAGE_SIZE_6);
        params.put("offset", offset);

        // 3. 쿼리 2개 호출
        List<ResQuestionItemDto> qnaList = storeMapper.selectReceivedQuestions(params);
        int totalCount = storeMapper.countReceivedQuestionsByStoreId(storeId);

        // 4. 총 페이지 수 계산
        int totalPages = (int) Math.ceil((double) totalCount / PAGE_SIZE_6);

        // 5. 결과를 DTO에 담아 반환 (React가 필요한 모든 정보)
        return new ResQuestionPageDto(qnaList, totalPages, totalCount);
    }

    public ResQuestionPageDto selectSentQuestions(Integer storeId, int currentPage) {
        // 1. offset 계산
        int offset = (currentPage - 1) * PAGE_SIZE_6;

        // 2. 맵퍼에 파라미터 전달
        Map<String, Object> params = new HashMap<>();
        params.put("storeId", storeId);
        params.put("limit", PAGE_SIZE_6);
        params.put("offset", offset);

        // 3. 쿼리 2개 호출
        List<ResQuestionItemDto> qnaList = storeMapper.selectSentQuestions(params);
        int totalCount = storeMapper.countSentQuestionsByStoreId(storeId);

        // 4. 총 페이지 수 계산
        int totalPages = (int) Math.ceil((double) totalCount / PAGE_SIZE_6);

        // 5. 결과를 DTO에 담아 반환 (React가 필요한 모든 정보)
        return new ResQuestionPageDto(qnaList, totalPages, totalCount);
    }

    @Transactional
    public void reportStore(Integer storeId, Integer memberNo) {
        int result = storeMapper.insertStoreReport(storeId, memberNo);
        if (result == 0) {
            throw new BusinessException(ResponseCode.INVALID_REQUEST);
        }
    }

    @Transactional
    public void blockStore(Integer storeId, Integer memberNo) {
        int result = storeMapper.insertStoreBlock(storeId, memberNo);
        if (result == 0) {
            throw new BusinessException(ResponseCode.INVALID_REQUEST);
        }
    }

    @Transactional
    public void insertFollow(Integer storeId, Integer memberNo) {
        int result = storeMapper.insertFollow(storeId, memberNo);
        if (result == 0) {
            throw new BusinessException(ResponseCode.INVALID_REQUEST);
        }
    }

    @Transactional
    public void deleteFollow(Integer storeId, Integer memberNo) {
        int result = storeMapper.deleteFollow(storeId, memberNo);
        if (result == 0) {
            throw new BusinessException(ResponseCode.NOT_FOUND);
        }
    }

    public ResStoreDetailDto selectMyStoreInfo(Integer memberNo) {
        ResStoreDetailDto storeDto = storeMapper.findMyStoreInfoById(memberNo);

        if (storeDto == null) {
            throw new IllegalArgumentException("Invalid member no: " + memberNo);
        }

        return storeDto;
    }

}
