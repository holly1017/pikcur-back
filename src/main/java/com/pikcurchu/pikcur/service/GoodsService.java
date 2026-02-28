package com.pikcurchu.pikcur.service;

import com.pikcurchu.pikcur.common.ResponseCode;
import com.pikcurchu.pikcur.dto.request.ReqGoodsDto;
import com.pikcurchu.pikcur.dto.response.*;
import com.pikcurchu.pikcur.exception.BusinessException;
import com.pikcurchu.pikcur.mapper.GoodsMapper;
import com.pikcurchu.pikcur.mapper.ImageMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GoodsService {
    private final GoodsMapper goodsMapper;
    private final ImageMapper imageMapper;
    private final FileService fileService;
    private static final int PAGE_SIZE_21 = 21;
    private static final int PAGE_SIZE_6 = 6;

    public List<ResGoodsItemDto> selectPopularGoodsList(Integer memberNo) {
        return goodsMapper.findPopularGoodsList(memberNo);
    }

    public List<ResGoodsItemDto> selectRecentViewGoodsList(Integer memberNo) {
        return goodsMapper.findRecentViewGoodsList(memberNo);
    }

    public List<ResGoodsItemDto> selectGoodsListByEndDate(Integer memberNo) {
        return goodsMapper.findGoodsByAuctionEndAsc(memberNo);
    }

    public List<ResCategoryDto> selectCategories() {
        return goodsMapper.findCategories();
    }

    public ResGoodsPageDto selectGoodsListByCategoryId(Integer categoryId, Integer memberNo, int currentPage) {
        int offset = (currentPage - 1) * PAGE_SIZE_21;

        Map<String, Object> params = new HashMap<>();
        params.put("categoryId", categoryId);
        params.put("memberNo", memberNo);
        params.put("limit", PAGE_SIZE_21);
        params.put("offset", offset);

        List<ResGoodsItemDto> goodsList = goodsMapper.findGoodsListByCategoryId(params);
        int totalCount = goodsMapper.countCategoryGoodsById(categoryId);

        int totalPages = (int) Math.ceil((double) totalCount / PAGE_SIZE_21);

        return new ResGoodsPageDto(goodsList, totalPages, totalCount);
    }

    @Transactional
    public ResGoodsDetailDto selectGoodsDetailById(Integer goodsId, Integer memberNo) {
        ResGoodsDetailDto goodsDetail = goodsMapper.findGoodsDetailById(goodsId, memberNo);
        if (goodsDetail == null)
            throw new BusinessException(ResponseCode.NOT_FOUND);

        goodsDetail.setImageList(imageMapper.findGoodsImages(goodsId));

        if (memberNo != null) {
            goodsMapper.insertGoodsHistory(goodsId, memberNo);
        }
        goodsMapper.updateGoodsView(goodsId);

        return goodsDetail;
    }

    @Transactional
    public void reportGoods(Integer goodsId, Integer memberNo) {
        int result = goodsMapper.insertGoodsReport(goodsId, memberNo);
        if (result == 0)
            throw new BusinessException(ResponseCode.INVALID_REQUEST);
    }

    @Transactional
    public void insertGoodsLike(Integer goodsId, Integer memberNo) {
        int result = goodsMapper.insertGoodsLike(goodsId, memberNo);
        if (result == 0)
            throw new BusinessException(ResponseCode.INVALID_REQUEST);
    }

    @Transactional
    public void deleteGoodsLike(Integer goodsId, Integer memberNo) {
        int result = goodsMapper.deleteGoodsLike(goodsId, memberNo);
        if (result == 0)
            throw new BusinessException(ResponseCode.NOT_FOUND);
    }

    @Transactional
    public Integer insertGoods(ReqGoodsDto reqGoodsDto, Integer memberNo) {
        reqGoodsDto.setMemberNo(memberNo);
        int result = goodsMapper.insertGoods(reqGoodsDto);
        if (result == 0)
            throw new BusinessException(ResponseCode.INVALID_REQUEST);

        return reqGoodsDto.getGoodsId();
    }

    public ResGoodsQuestionsPageDto selectGoodsQuestionsById(Integer goodsId, int currentPage) {
        int offset = (currentPage - 1) * PAGE_SIZE_6;

        Map<String, Object> params = new HashMap<>();
        params.put("goodsId", goodsId);
        params.put("limit", PAGE_SIZE_6);
        params.put("offset", offset);

        List<ResGoodsQuestionsDto> questionList = goodsMapper.selectGoodsQuestionsById(params);
        int totalCount = goodsMapper.countSellTransactionByStoreId(goodsId);

        int totalPages = (int) Math.ceil((double) totalCount / PAGE_SIZE_6);

        return new ResGoodsQuestionsPageDto(questionList, totalPages, totalCount);
    }

    @Transactional
    public void insertGoodsImages(Integer goodsId, List<MultipartFile> images) {
        for (int i = 0; i < images.size(); i++) {
            MultipartFile img = images.get(i);
            String uploadedPath = fileService.goodsUploadFile(img);
            int result = imageMapper.insertGoodsImage(goodsId, uploadedPath, i + 1);
            if (result == 0)
                throw new BusinessException(ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }
}
