package com.woopaca.likeknu.controller;

import com.woopaca.likeknu.Campus;
import com.woopaca.likeknu.controller.dto.base.ResponseDto;
import com.woopaca.likeknu.controller.dto.menu.CafeteriaListResponse;
import com.woopaca.likeknu.controller.dto.menu.CafeteriaMealListResponse;
import com.woopaca.likeknu.entity.Cafeteria;
import com.woopaca.likeknu.exception.BusinessException;
import com.woopaca.likeknu.repository.CafeteriaRepository;
import com.woopaca.likeknu.service.MenuService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/api/v2/menus")
public class MenuControllerV2 {

    private final CafeteriaRepository cafeteriaRepository;
    private final MenuService menuService;

    public MenuControllerV2(CafeteriaRepository cafeteriaRepository, MenuService menuService) {
        this.cafeteriaRepository = cafeteriaRepository;
        this.menuService = menuService;
    }

    @GetMapping("/cafeterias")
    public ResponseDto<List<CafeteriaListResponse>> getCafeteriasByCampus(@RequestParam("campus") Campus campus) {
        List<CafeteriaListResponse> cafeterias = cafeteriaRepository.findByCampus(campus)
                .stream()
                .sorted(Comparator.comparing(Cafeteria::getSequence))
                .map(CafeteriaListResponse::from)
                .toList();
        return ResponseDto.of(cafeterias);
    }

    @GetMapping
    public ResponseDto<List<CafeteriaMealListResponse>> getMenuByCampus(
            @RequestParam("cafeteriaId") String cafeteriaId
    ) {
        List<CafeteriaMealListResponse> cafeteriaMeals = menuService.getCafeteriaMealsV2(cafeteriaId);
        return ResponseDto.of(cafeteriaMeals);
    }
}
