package com.pikcurchu.pikcur.service;

import com.pikcurchu.pikcur.common.ResponseCode;
import com.pikcurchu.pikcur.dto.response.ResGoodsItemDto;
import com.pikcurchu.pikcur.dto.response.ResGoodsPageDto;
import com.pikcurchu.pikcur.dto.response.ResSearchDto;
import com.pikcurchu.pikcur.exception.BusinessException;
import com.pikcurchu.pikcur.mapper.SearchMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SearchService {
    private final SearchMapper searchMapper;
    private static final int PAGE_SIZE_24 = 24;

    @Transactional
    public ResGoodsPageDto selectSearchGoodsList(String keyword, Integer memberNo, int currentPage) {
        int offset = (currentPage - 1) * PAGE_SIZE_24;

        Map<String, Object> params = new HashMap<>();
        params.put("keyword", keyword);
        params.put("memberNo", memberNo);
        params.put("limit", PAGE_SIZE_24);
        params.put("offset", offset);

        List<ResGoodsItemDto> goodsList = searchMapper.selectSearchGoodsList(params);
        if (memberNo != null) {
            int result = searchMapper.insertSearchHistory(keyword, memberNo);
            if (result == 0) {
                throw new BusinessException(ResponseCode.INTERNAL_SERVER_ERROR);
            }
            searchMapper.deleteOldSearchHistory(memberNo);
        }
        int totalCount = searchMapper.countSearchGoods(keyword);

        int totalPages = (int) Math.ceil((double) totalCount / PAGE_SIZE_24);

        return new ResGoodsPageDto(goodsList, totalPages, totalCount);
    }

    public List<ResSearchDto> selectSearchHistory(Integer memberNo) {
        return searchMapper.selectSearchHistory(memberNo);
    }

    public List<ResSearchDto> selectSearchTop10() {
        return searchMapper.selectSearchTop10();
    }

    @Transactional
    public void deleteSearchHistory(Integer memberNo, Integer searchHistoryId) {
        int result = searchMapper.deleteSearchHistory(memberNo, searchHistoryId);
        if (result == 0) {
            throw new BusinessException(ResponseCode.NOT_FOUND);
        }
    }
}
