package ac.knu.likeknu.controller.dto.base;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class MenuResponseData<T> implements ResponseData<T> {

    private final T body;
    private final LocalDate date;

    public MenuResponseData(T body, LocalDate date) {
        this.body = body;
        this.date = date;
    }
}
