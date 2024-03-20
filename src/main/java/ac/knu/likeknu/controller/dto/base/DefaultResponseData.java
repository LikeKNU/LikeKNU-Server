package ac.knu.likeknu.controller.dto.base;

public record DefaultResponseData<T>(T body) implements ResponseData<T> {
}
