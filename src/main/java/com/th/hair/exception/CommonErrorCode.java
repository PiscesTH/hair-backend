package com.th.hair.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CommonErrorCode implements ErrorCode{
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버에서 에러가 발생하였습니다."),
    NOT_EXIST_FILE(HttpStatus.BAD_REQUEST, "존재하지 않는 파일입니다.");


    private final HttpStatus httpStatus;
    private final String message;

}
