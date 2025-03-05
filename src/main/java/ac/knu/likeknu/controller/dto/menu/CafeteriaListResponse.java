package ac.knu.likeknu.controller.dto.menu;

import ac.knu.likeknu.domain.Cafeteria;

public record CafeteriaListResponse(String cafeteriaId, String cafeteriaName) {

    public static CafeteriaListResponse from(Cafeteria cafeteria) {
        return new CafeteriaListResponse(cafeteria.getId(), cafeteria.getCafeteriaName());
    }
}
