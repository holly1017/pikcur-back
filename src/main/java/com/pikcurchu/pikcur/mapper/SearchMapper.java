package com.pikcurchu.pikcur.mapper;

import com.pikcurchu.pikcur.dto.response.ResGoodsItemDto;
import com.pikcurchu.pikcur.dto.response.ResSearchDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface SearchMapper {
    List<ResGoodsItemDto> selectSearchGoodsList(Map<String, Object> params);

    List<ResSearchDto> selectSearchHistory(Integer memberNo);

    List<ResSearchDto> selectSearchTop10();

    void insertSearchHistory(String keyword, Integer memberNo);

    void deleteOldSearchHistory(Integer memberNo);

    int countSearchGoods(String keyword);

    Integer deleteSearchHistory(Integer memberNo, Integer searchHistoryId);
}
