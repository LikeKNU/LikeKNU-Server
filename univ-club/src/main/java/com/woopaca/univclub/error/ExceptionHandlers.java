package com.woopaca.univclub.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@Slf4j
@RestControllerAdvice
public class ExceptionHandlers {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResponse handleIllegalArgumentException(IllegalArgumentException exception) {
        log.warn("프론트 겁나 잘못함", exception);
        return ErrorResponse.ofBadRequest(exception.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ErrorResponse handleMaxUploadSizeExceededException(MaxUploadSizeExceededException exception) {
        log.warn("프론트 겁나 잘못함", exception);
        return ErrorResponse.ofBadRequest("파일 5MB 까지만 가능임 ㅅㄱ");
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorResponse handleException(Exception exception) {
        log.error("프론트 잘못일듯", exception);
        return ErrorResponse.ofInternalServerError(exception.getMessage());
    }

    public record ErrorResponse(String code, String message) {

        public static ErrorResponse ofBadRequest(String message) {
            return new ErrorResponse("응 프론트 잘못~", message);
        }

        public static ErrorResponse ofInternalServerError(String message) {
            return new ErrorResponse("응 서버 잘못일 수도 있지만 프론트 잘못일 확률이 큼~", message);
        }
    }
}
