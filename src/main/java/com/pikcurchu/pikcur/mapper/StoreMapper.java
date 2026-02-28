package com.pikcurchu.pikcur.mapper;

import com.pikcurchu.pikcur.dto.request.ReqStoreBlockDto;
import com.pikcurchu.pikcur.dto.request.ReqStoreReportDto;
import com.pikcurchu.pikcur.dto.response.*;
import com.pikcurchu.pikcur.vo.GoodsLike;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface StoreMapper {
    ResStoreDetailDto findStoreInfoById(Integer storeId, Integer currentMemberNo);

    List<ResReviewItemDto> findStoreReviewById(Map<String, Object> params);

    ResStoreAverageDto findStoreReviewRatingAverage(Integer storeId);

    List<ResGoodsItemDto> findStoreGoodsById(Map<String, Object> params);

    List<ResTransactionItemDto> findStoreSellTranactionById(Map<String, Object> params);

    List<ResTransactionItemDto> findStoreBuyTranactionById(Map<String, Object> params);

    List<ResBidItemDto> findBidById(Map<String, Object> params);

    List<ResGoodsItemDto> findGoodsLikeById(Map<String, Object> params);

    List<ResBrandItemDto> findBrandLikeById(Map<String, Object> params);

    List<ResFollowItemDto> findFollowById(Map<String, Object> params);

    List<ResQuestionItemDto> selectReceivedQuestions(Map<String, Object> params);

    List<ResQuestionItemDto> selectSentQuestions(Map<String, Object> params);

    int insertStoreReport(Integer storeId, Integer memberNo);

    int insertStoreBlock(Integer storeId, Integer memberNo);

    int countReceivedQuestionsByStoreId(Integer storeId);

    int countSentQuestionsByStoreId(Integer storeId);

    int countReviewsByStoreId(Integer storeId);

    int countSellTransactionByStoreId(Integer storeId);

    int countBuyTransactionByStoreId(Integer storeId);

    int countBidsByStoreId(Integer storeId);

    int countBrandLikeByStoreId(Integer storeId);

    int countFollowByStoreId(Integer storeId);

    int countStoreGoodsByStoreId(Integer storeId);

    int countGoodsLikeGoodsByStoreId(Integer storeId);

    int insertFollow(Integer storeId, Integer memberNo);

    int deleteFollow(Integer storeId, Integer memberNo);

    ResStoreDetailDto findMyStoreInfoById(Integer memberNo);

    List<ResBidItemDto> findWinBidById(Map<String, Object> params);

    int countWinBidsByStoreId(Integer storeId);
}
