package com.pikcurchu.pikcur.dto.response;

import com.pikcurchu.pikcur.vo.Faq;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResFaqPageDto {
    private List<Faq> faqList;
    private int totalPages;
    private int totalCount;
}
