package com.pikcurchu.pikcur.common;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 모든 API 응답의 표준 규격을 정의하는 공통 응답 객체입니다.
 * 성공 여부, 결과 코드, 메시지, 실제 데이터, HTTP 상태 코드를 포함합니다.
 */
@Data
@NoArgsConstructor
@Builder
public class ApiResponse<T> {
    private boolean success; // 요청 성공 여부
    private String code; // 비즈니스 응답 코드 (예: SUCCESS, USER_NOT_FOUND 등)
    private String message; // 사용자에게 보여줄 응답 메시지
    private T data; // 실제 응답 데이터 (성공 시에만 포함)
    private Integer httpStatus; // HTTP 상태 코드 (200, 400, 500 등)

    public ApiResponse(boolean success, String code, String message, T data, Integer httpStatus) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
        this.httpStatus = httpStatus;
    }

    /**
     * 성공 응답을 생성하는 정적 팩토리 메서드
     * 
     * @param data 클라이언트에 전달할 실제 데이터
     */
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(), data, 200);
    }

    /**
     * 실패 응답을 생성하는 정적 팩토리 메서드 (ResponseCode 기반)
     * 
     * @param responseCode 응답 코드 열거형
     * @param httpStatus   가변적인 HTTP 상태 코드
     */
    public static <T> ApiResponse<T> fail(ResponseCode responseCode, Integer httpStatus) {
        return new ApiResponse<>(false, responseCode.getCode(), responseCode.getMessage(), null, httpStatus);
    }

    /**
     * 실패 응답을 생성하는 정적 팩토리 메서드 (커스텀 메시지 기반)
     */
    public static <T> ApiResponse<T> fail(String code, String message, Integer httpStatus) {
        return new ApiResponse<>(false, code, message, null, httpStatus);
    }
}
