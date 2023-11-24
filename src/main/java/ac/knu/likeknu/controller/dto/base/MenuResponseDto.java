package ac.knu.likeknu.controller.dto.base;

import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class MenuResponseDto<T> extends ResponseDto<T> {

    protected MenuResponseDto(LocalDateTime timeStamp, String message, T data, LocalDate date) {
        super(timeStamp, message, new MenuResponseData<>(data, date));
    }

    public static <T> MenuResponseDto<T> of(String message, LocalDate date) {
        return new MenuResponseDto<>(LocalDateTime.now(), message, null, date);
    }

    public static <T> MenuResponseDto<T> of(T body, LocalDate date) {
        return new MenuResponseDto<>(LocalDateTime.now(), null, body, date);
    }

    public static <T> MenuResponseDto<T> of(T body, String message, LocalDate date) {
        return new MenuResponseDto<>(LocalDateTime.now(), message, body, date);
    }
}
