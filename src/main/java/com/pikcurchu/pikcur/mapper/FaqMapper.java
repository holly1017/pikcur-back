package com.pikcurchu.pikcur.mapper;

import com.pikcurchu.pikcur.vo.Faq;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface FaqMapper {
    List<Faq> selectFaq(Map<String, Object> params);

    Integer countFaq();
}
