package com.pikcurchu.pikcur.service;

import com.pikcurchu.pikcur.common.ResponseCode;
import com.pikcurchu.pikcur.dto.request.ReqBidDto;
import com.pikcurchu.pikcur.dto.response.ResGoodsBidItemDto;
import com.pikcurchu.pikcur.dto.response.ResGoodsBidsPageDto;
import com.pikcurchu.pikcur.exception.BusinessException;
import com.pikcurchu.pikcur.mapper.BidMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BidService {
    private final BidMapper bidMapper;
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

        int result = bidMapper.insertBid(reqBidDto);
        if (result == 0) {
            throw new BusinessException(ResponseCode.INVALID_REQUEST);
        }
    }
}
