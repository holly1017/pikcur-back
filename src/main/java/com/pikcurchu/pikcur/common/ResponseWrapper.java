package com.pikcurchu.pikcur.common;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import org.springframework.lang.Nullable;

/**
 * 모든 컨트롤러의 응답을 가로채서 ApiResponse로 자동 래핑합니다.
 * 이 클래스를 통해 컨트롤러에서 매번 ApiResponse를 작성할 필요 없이,
 * 데이터 객체만 반환하면 규격에 맞는 JSON 응답이 자동 생성됩니다.
 */
@RestControllerAdvice(basePackages = "com.pikcurchu.pikcur.controller")
public class ResponseWrapper implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        // 인터셉터를 적용할 범위를 지정합니다. 기본적으로 모든 컨트롤러 응답에 적용합니다.
        return true;
    }

    @Override
    public Object beforeBodyWrite(@Nullable Object body, MethodParameter returnType, MediaType selectedContentType,
            Class<? extends HttpMessageConverter<?>> selectedConverterType,
            ServerHttpRequest request, ServerHttpResponse response) {

        // 1. 이미 ApiResponse 형태라면 (예: 에러 처리기에서 반환한 경우) 그대로 반환하여 이중 래핑 방지
        if (body instanceof ApiResponse) {
            return body;
        }

        // 2. String인 경우 HttpMessageConverter 특성상 직접 래핑 시 ClassCastException이 발생할 수 있어
        // 별도의 처리가 권장되지만, 여기서는 기본 래핑을 시도합니다.
        if (body instanceof String) {
            return ApiResponse.success(body);
        }

        // 3. 그 외의 모든 일반 데이터 객체(DTO, List 등)를 ApiResponse.success()로 감싸서 공통 규격 적용
        return ApiResponse.success(body);
    }
}
