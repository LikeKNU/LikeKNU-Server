package ac.knu.likeknu.controller.dto.response;

import lombok.Getter;

@Getter
public class PageDto {

    private final int currentPage;
    private int totalPages;

    protected PageDto(int currentPage) {
        this.currentPage = currentPage;
    }

    public void updateTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public static PageDto of(int currentPage) {
        if (currentPage < 1) {
            //TODO Custom exception
        }
        return new PageDto(currentPage);
    }
}
