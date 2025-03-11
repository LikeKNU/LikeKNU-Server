package ac.knu.likeknu.controller;

import ac.knu.likeknu.controller.dto.base.ResponseDto;
import ac.knu.likeknu.controller.dto.menu.CafeteriaListResponse;
import ac.knu.likeknu.controller.dto.menu.CafeteriaMealListResponse;
import ac.knu.likeknu.domain.Cafeteria;
import ac.knu.likeknu.domain.constants.Campus;
import ac.knu.likeknu.exception.BusinessException;
import ac.knu.likeknu.repository.CafeteriaRepository;
import ac.knu.likeknu.service.MenuService;
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
            @RequestParam("campus") Campus campus,
            @RequestParam("cafeteriaId") String cafeteriaId
    ) {
        if (campus.equals(Campus.ALL)) {
            throw new BusinessException("Invalid campus");
        }

        List<CafeteriaMealListResponse> cafeteriaMeals = menuService.getCafeteriaMealsV2(campus, cafeteriaId);
        return ResponseDto.of(cafeteriaMeals);
    }
}
