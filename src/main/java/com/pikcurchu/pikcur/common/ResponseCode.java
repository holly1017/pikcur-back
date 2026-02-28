package com.pikcurchu.pikcur.common;

public enum ResponseCode {
    SUCCESS("SUCCESS", "성공"),
    DUPLICATE("DUPLICATE", "중복 데이터"),
    MAIL_SEND_FAIL("MAIL_SEND_FAIL", "메일 전송 실패"),
    INVALID_REQUEST("INVALID_REQUEST", "잘못된 요청"),
    EXPIRATION("EXPIRATION", "만료"),
    INCONSISTENCY("INCONSISTENCY", "불일치"),
    NOT_FOUND("NOT_FOUND", "존재 하지 않음"),
    REQUIRED_IMAGE("REQUIRED_IMAGE", "이미지를 1장 이상 추가해 주세요."),

    // Auth & User
    USER_NOT_FOUND("USER_NOT_FOUND", "사용자를 찾을 수 없습니다."),
    INVALID_PASSWORD("INVALID_PASSWORD", "비밀번호가 일치하지 않습니다."),
    UNAUTHORIZED("UNAUTHORIZED", "인증 정보가 유효하지 않습니다."),
    FORBIDDEN("FORBIDDEN", "권한이 없습니다."),

    // Validation & System
    VALIDATION_ERROR("VALIDATION_ERROR", "입력값이 유효하지 않습니다."),
    INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR", "서버 내부 오류가 발생했습니다.");

    private final String code;
    private final String message;

    ResponseCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
