package com.pikcurchu.pikcur.service;

import com.pikcurchu.pikcur.common.ResponseCode;
import com.pikcurchu.pikcur.dto.request.ReqBidDto;
import com.pikcurchu.pikcur.dto.response.ResGoodsBidItemDto;
import com.pikcurchu.pikcur.dto.response.ResGoodsBidsPageDto;
import com.pikcurchu.pikcur.dto.response.ResRealTimeBidDto;
import com.pikcurchu.pikcur.exception.BusinessException;
import com.pikcurchu.pikcur.mapper.BidMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BidService {
    private final BidMapper bidMapper;
    private final SimpMessagingTemplate messagingTemplate;
    private static final int PAGE_SIZE_6 = 6;

    public ResGoodsBidsPageDto selectBidList(Integer goodsId, int currentPage) {
        int offset = (currentPage - 1) * PAGE_SIZE_6;

        Map<String, Object> params = new HashMap<>();
        params.put("goodsId", goodsId);
        params.put("limit", PAGE_SIZE_6);
        params.put("offset", offset);

        List<ResGoodsBidItemDto> bidList = bidMapper.selectBidList(params);
        int totalCount = bidMapper.countGoodsBidsByGoodsId(goodsId);

        int totalPages = (int) Math.ceil((double) totalCount / PAGE_SIZE_6);

        return new ResGoodsBidsPageDto(bidList, totalPages, totalCount);
    }

    @Transactional
    public void insertBid(Integer goodsId, ReqBidDto reqBidDto, Integer memberNo) {
        reqBidDto.setGoodsId(goodsId);
        reqBidDto.setMemberNo(memberNo);

        // 1. FOR UPDATE: 해당 상품의 입찰 테이블에 Row Lock을 획득합니다.
        // 동시에 여러 요청이 들어와도 한 번에 하나씩 순차적으로 처리됩니다.
        int currentMax = bidMapper.selectMaxPriceForUpdate(goodsId);

        // 2. 서버 측 가격 검증
        // 현재 최고가(또는 시작가)보다 낮거나 같은 금액이면 입찰을 거절합니다.
        if (reqBidDto.getBidPrice() == null || reqBidDto.getBidPrice() <= currentMax) {
            throw new BusinessException(ResponseCode.INVALID_REQUEST);
        }

        // 3. 검증 통과 후 입찰 저장
        int result = bidMapper.insertBid(reqBidDto);
        if (result == 0) {
            throw new BusinessException(ResponseCode.INVALID_REQUEST);
        }

        // 4. 입찰 성공 시 실시간 WebSocket 푸시
        // 해당 상품을 보고 있는 모든 사용자에게 새 입찰 정보를 전달합니다.
        // 민감 정보(memberNo)를 제외한 전용 DTO를 사용합니다.
        ResRealTimeBidDto realTimeBid = new ResRealTimeBidDto(goodsId, reqBidDto.getBidPrice(), "");
        messagingTemplate.convertAndSend("/topic/goods/" + goodsId, realTimeBid);
    }

}
