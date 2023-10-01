package ac.knu.likeknu.controller.dto.response;

import lombok.Getter;

@Getter
public class PageResponseData<T> implements ResponseData<T> {

    private final T body;
    private final PageDto page;

    public PageResponseData(T body, PageDto page) {
        this.body = body;
        this.page = page;
    }
}
