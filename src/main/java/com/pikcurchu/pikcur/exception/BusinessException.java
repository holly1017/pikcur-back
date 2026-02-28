package com.pikcurchu.pikcur.exception;

import com.pikcurchu.pikcur.common.ResponseCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * 프로젝트 전체에서 사용되는 공통 비즈니스 예외 클래스입니다.
 * RuntimeException을 상속받아 서비스 레이어에서 체크 예외 처리 없이 자유롭게 발생시킬 수 있습니다.
 * ResponseCode와 HttpStatus를 필드로 가집니다.
 */
@Getter
public class BusinessException extends RuntimeException {
    private final ResponseCode responseCode;
    private final HttpStatus httpStatus;

    /**
     * 기본 비즈니스 예외 (상태 코드는 400 Bad Request가 기본값)
     */
    public BusinessException(ResponseCode responseCode) {
        super(responseCode.getMessage());
        this.responseCode = responseCode;
        this.httpStatus = HttpStatus.BAD_REQUEST;
    }

    /**
     * 특정 HTTP 상태 코드를 지정하고 싶을 때 사용하는 생성자
     */
    public BusinessException(ResponseCode responseCode, HttpStatus httpStatus) {
        super(responseCode.getMessage());
        this.responseCode = responseCode;
        this.httpStatus = httpStatus;
    }
}
