package com.pikcurchu.pikcur.mapper;

import com.pikcurchu.pikcur.dto.request.ReqGoodsDto;
import com.pikcurchu.pikcur.dto.response.ResCategoryDto;
import com.pikcurchu.pikcur.dto.response.ResGoodsItemDto;
import com.pikcurchu.pikcur.dto.response.ResGoodsDetailDto;
import com.pikcurchu.pikcur.dto.response.ResGoodsQuestionsDto;
import com.pikcurchu.pikcur.vo.Goods;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface GoodsMapper {
    List<ResGoodsItemDto> findPopularGoodsList(Integer memberNo);

    List<ResGoodsItemDto> findRecentViewGoodsList(Integer memberNo);

    List<ResGoodsItemDto> findGoodsByAuctionEndAsc(Integer memberNo);

    List<ResCategoryDto> findCategories();

    List<ResGoodsItemDto> findGoodsListByCategoryId(Map<String, Object> params);

    ResGoodsDetailDto findGoodsDetailById(Integer goodsId, Integer memberNo);

    List<ResGoodsQuestionsDto> selectGoodsQuestionsById(Map<String, Object> params);

    int insertGoodsReport(Integer goodsId, Integer memberNo);

    int insertGoodsLike(Integer goodsId, Integer memberNo);

    int deleteGoodsLike(Integer goodsId, Integer memberNo);

    void insertGoodsHistory(Integer goodsId, Integer memberNo);

    void updateGoodsView(Integer goodsId);

    int insertGoods(ReqGoodsDto reqGoodsDto);

    List<Goods> findExpiredGoods();

    int updateGoodsStatus(String statusNo, Integer goodsId);

    Integer selectMemberNoOfStore(Integer storeId);

    Integer selectGoodsMemberNo(Integer goodsId);

    int countCategoryGoodsById(Integer categoryId);

    int countSellTransactionByStoreId(Integer goodsId);
}
