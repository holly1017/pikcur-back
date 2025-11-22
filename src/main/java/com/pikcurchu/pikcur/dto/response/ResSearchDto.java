package com.pikcurchu.pikcur.dto.response;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ResSearchDto {
    private String keyword;
    private Integer searchHistoryId;
}
