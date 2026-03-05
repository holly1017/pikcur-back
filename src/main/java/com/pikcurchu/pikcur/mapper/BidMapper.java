package com.pikcurchu.pikcur.mapper;

import com.pikcurchu.pikcur.dto.request.ReqBidDto;
import com.pikcurchu.pikcur.dto.response.ResGoodsBidItemDto;
import com.pikcurchu.pikcur.vo.Bid;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface BidMapper {
    List<ResGoodsBidItemDto> selectBidList(Map<String, Object> params);

    int insertBid(ReqBidDto reqBidDto);

    int updateBidStatus(String statusNo, Integer goodsId, Integer bidId);

    Bid findTopBidder(Integer goodsId);

    int countGoodsBidsByGoodsId(Integer goodsId);

    List<Bid> findUnpaidWinners();

    Bid findNextBidder(Integer goodsId);

    /**
     * 동시성 제어: 입찰 처리 시 Row Lock을 획득하여 현재 최고가를 조회합니다.
     * SELECT ... FOR UPDATE를 사용하여 동일 상품에 대한 동시 입찰을 순차적으로 처리합니다.
     */
    int selectMaxPriceForUpdate(Integer goodsId);
}
