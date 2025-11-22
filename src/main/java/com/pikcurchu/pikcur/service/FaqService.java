package com.pikcurchu.pikcur.service;

import com.pikcurchu.pikcur.dto.response.ResFaqPageDto;
import com.pikcurchu.pikcur.dto.response.ResGoodsItemDto;
import com.pikcurchu.pikcur.dto.response.ResGoodsPageDto;
import com.pikcurchu.pikcur.mapper.FaqMapper;
import com.pikcurchu.pikcur.vo.Faq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class FaqService {
    private final FaqMapper faqMapper;
    private static final int PAGE_SIZE_5 = 5;

    public ResFaqPageDto selectFaq(int currentPage) {
        int offset = (currentPage - 1) * PAGE_SIZE_5;

        Map<String, Object> params = new HashMap<>();
        params.put("limit", PAGE_SIZE_5);
        params.put("offset", offset);

        List<Faq> faqList = faqMapper.selectFaq(params);
        int totalCount = faqMapper.countFaq();

        int totalPages = (int) Math.ceil((double) totalCount / PAGE_SIZE_5);

        return new ResFaqPageDto(faqList, totalPages, totalCount);
    }
}

