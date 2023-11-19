package ac.knu.likeknu.controller.dto.base;

import lombok.Getter;

@Getter
public class DefaultResponseData<T> implements ResponseData<T> {
    private final T body;

    protected DefaultResponseData(T body) {
        this.body = body;
    }
}
