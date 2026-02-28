package com.pikcurchu.pikcur.exception;

import com.pikcurchu.pikcur.common.ApiResponse;
import com.pikcurchu.pikcur.common.ResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 프로젝트 전역에서 발생하는 예외를 한곳에서 처리하는 핸들러 클래스입니다.
 * 각 컨트롤러에서 발생하는 예외를 가로채서 표준 응답 규격(ApiResponse)으로 변환합니다.
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 비즈니스 로직 상의 예외 처리 (BusinessException)
     * 서비스 레이어에서 명시적으로 던진 예외를 가로채서 처리합니다.
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponse<Void>> handleBusinessException(BusinessException e) {
        log.error("BusinessException: code={}, message={}", e.getResponseCode().getCode(), e.getMessage());
        return ResponseEntity
                .status(e.getHttpStatus())
                .body(ApiResponse.fail(e.getResponseCode(), e.getHttpStatus().value()));
    }

    /**
     * Bean Validation 기능 사용 시 발생하는 검증 예외 처리 (@Valid)
     * 컨트롤러 진입 전 파라미터 유효성 검사 실패 시 호출됩니다.
     */
    @ExceptionHandler({ MethodArgumentNotValidException.class, BindException.class })
    public ResponseEntity<ApiResponse<Void>> handleValidationException(Exception e) {
        String message = "검증 오류가 발생했습니다.";
        // 첫 번째 에러 메시지를 추출하여 클라이언트에 전달
        if (e instanceof MethodArgumentNotValidException) {
            message = ((MethodArgumentNotValidException) e).getBindingResult().getAllErrors().get(0)
                    .getDefaultMessage();
        } else if (e instanceof BindException) {
            message = ((BindException) e).getBindingResult().getAllErrors().get(0).getDefaultMessage();
        }

        log.error("ValidationException: {}", message);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.fail(ResponseCode.VALIDATION_ERROR.getCode(), message, 400));
    }

    /**
     * 그 외 예상치 못한 모든 상위 예외 처리
     * 서버 내부 오류(500) 상황에서 가독성 있는 응답을 내보내기 위해 사용합니다.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleException(Exception e) {
        log.error("Unhandled Exception: ", e);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.fail(ResponseCode.INTERNAL_SERVER_ERROR, 500));
    }
}
