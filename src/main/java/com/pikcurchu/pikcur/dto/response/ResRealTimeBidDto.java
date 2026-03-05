package com.pikcurchu.pikcur.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 실시간 입찰 발생 시 WebSocket을 통해 구독자에게 전달되는 DTO입니다.
 * 민감한 memberNo 등을 직접 노출하지 않고 필요한 정보만 전달합니다.
 */
@Getter
@AllArgsConstructor
public class ResRealTimeBidDto {
    /** 상품 ID */
    private Integer goodsId;
    /** 새로운 입찰가 */
    private Integer bidPrice;
    /** 입찰자 아이디 (마스킹 처리하여 전달) */
    private String memberId;
}
