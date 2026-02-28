package com.pikcurchu.pikcur.service;

import com.pikcurchu.pikcur.common.ResponseCode;
import com.pikcurchu.pikcur.dto.response.ResBrandDetailDto;
import com.pikcurchu.pikcur.dto.response.ResBrandListDto;
import com.pikcurchu.pikcur.dto.response.ResGoodsItemDto;
import com.pikcurchu.pikcur.dto.response.ResGoodsPageDto;
import com.pikcurchu.pikcur.exception.BusinessException;
import com.pikcurchu.pikcur.mapper.BrandMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BrandService {
    private final BrandMapper brandMapper;
    private static final int PAGE_SIZE_21 = 21;

    public ResBrandDetailDto selectBrandDetail(Integer brandId, Integer memberNo) {
        ResBrandDetailDto brandDetailDto = brandMapper.selectBrandDetail(brandId, memberNo);
        if (brandDetailDto == null) {
            throw new BusinessException(ResponseCode.NOT_FOUND);
        }
        brandDetailDto.setGoodsCount(brandMapper.selectBrandGoodsCount(brandId));
        return brandDetailDto;
    }

    public ResGoodsPageDto selectBrandGoodsList(Integer brandId, Integer memberNo, int currentPage) {
        int offset = (currentPage - 1) * PAGE_SIZE_21;

        Map<String, Object> params = new HashMap<>();
        params.put("brandId", brandId);
        params.put("memberNo", memberNo);
        params.put("limit", PAGE_SIZE_21);
        params.put("offset", offset);

        List<ResGoodsItemDto> goodsList = brandMapper.selectBrandGoodsList(params);
        int totalCount = brandMapper.countBrandGoodsByBrandId(brandId);

        int totalPages = (int) Math.ceil((double) totalCount / PAGE_SIZE_21);

        return new ResGoodsPageDto(goodsList, totalPages, totalCount);
    }

    @Transactional
    public void insertBrandLike(Integer brandId, Integer memberNo) {
        int result = brandMapper.insertBrandLike(brandId, memberNo);
        if (result == 0) {
            throw new BusinessException(ResponseCode.INVALID_REQUEST);
        }
    }

    @Transactional
    public void deleteBrandLike(Integer brandId, Integer memberNo) {
        int result = brandMapper.deleteBrandLike(brandId, memberNo);
        if (result == 0) {
            throw new BusinessException(ResponseCode.NOT_FOUND);
        }
    }

    public List<ResBrandListDto> selectBrandList() {
        return brandMapper.selectBrandList();
    }
}
