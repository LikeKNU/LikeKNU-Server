package com.woopaca.likeknu.controller.dto.base;

import com.woopaca.likeknu.exception.BusinessException;
import lombok.Getter;

@Getter
public class PageDto {

    private final int currentPage;
    private int totalPages;
    private long totalElements;

    protected PageDto(int currentPage, int totalPages, long totalElements) {
        this.currentPage = currentPage;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
    }

    public void updateTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public void updateTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public static PageDto of(int currentPage) {
        if (currentPage < 1) {
            throw new BusinessException("Invalid page!");
        }
        return new PageDto(currentPage, 1, 1);
    }
}
