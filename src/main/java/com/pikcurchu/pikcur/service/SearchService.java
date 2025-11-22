package com.pikcurchu.pikcur.service;

import com.pikcurchu.pikcur.dto.response.ResGoodsItemDto;
import com.pikcurchu.pikcur.dto.response.ResGoodsPageDto;
import com.pikcurchu.pikcur.dto.response.ResSearchDto;
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

        // 2. 맵퍼에 파라미터 전달
        Map<String, Object> params = new HashMap<>();
        params.put("keyword", keyword);
        params.put("memberNo", memberNo);
        params.put("limit", PAGE_SIZE_24);
        params.put("offset", offset);

        // 3. 쿼리 2개 호출
        List<ResGoodsItemDto> goodsList = searchMapper.selectSearchGoodsList(params);
        if(memberNo != null) {
            searchMapper.insertSearchHistory(keyword, memberNo);
            searchMapper.deleteOldSearchHistory(memberNo);
        }
        int totalCount = searchMapper.countSearchGoods(keyword);

        // 4. 총 페이지 수 계산
        int totalPages = (int) Math.ceil((double) totalCount / PAGE_SIZE_24);

        // 5. 결과를 DTO에 담아 반환 (React가 필요한 모든 정보)
        return new ResGoodsPageDto(goodsList, totalPages, totalCount);
    }

    public List<ResSearchDto> selectSearchHistory(Integer memberNo) {
        return searchMapper.selectSearchHistory(memberNo);
    }

    public List<ResSearchDto> selectSearchTop10() {
        return searchMapper.selectSearchTop10();
    }

    public Integer deleteSearchHistory(Integer memberNo, Integer searchHistoryId) {
        return searchMapper.deleteSearchHistory(memberNo, searchHistoryId);
    }
}
