package ac.knu.likeknu.controller.dto.base;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PageResponseDto<T> extends ResponseDto<T> {

    protected PageResponseDto(LocalDateTime timeStamp, String message, T data, PageDto pageDto) {
        super(timeStamp, message, new PageResponseData<>(data, pageDto));
    }

    public static <T> PageResponseDto<T> of(String message, PageDto pageDto) {
        return new PageResponseDto<>(LocalDateTime.now(), message, null, pageDto);
    }

    public static <T> PageResponseDto<T> of(T body, PageDto pageDto) {
        return new PageResponseDto<>(LocalDateTime.now(), null, body, pageDto);
    }

    public static <T> PageResponseDto<T> of(T body, String message, PageDto pageDto) {
        return new PageResponseDto<>(LocalDateTime.now(), message, body, pageDto);
    }
}
