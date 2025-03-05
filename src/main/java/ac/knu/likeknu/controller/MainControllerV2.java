package ac.knu.likeknu.controller;

import ac.knu.likeknu.controller.dto.base.ResponseDto;
import ac.knu.likeknu.controller.dto.menu.MainMenuResponseV2;
import ac.knu.likeknu.domain.constants.Campus;
import ac.knu.likeknu.exception.BusinessException;
import ac.knu.likeknu.service.MainService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@RestController
@RequestMapping("/api/v2/main")
public class MainControllerV2 {

    private final MainService mainService;

    public MainControllerV2(MainService mainService) {
        this.mainService = mainService;
    }

    @GetMapping("/menus")
    public ResponseDto<List<MainMenuResponseV2>> getMainMenu(@RequestParam(name = "campus", defaultValue = "CHEONAN") Campus campus) {
        if (campus.equals(Campus.ALL)) {
            throw new BusinessException("Invalid campus");
        }

        List<MainMenuResponseV2> responses = mainService.getMenuResponseV2(campus);
        return ResponseDto.of(responses);
    }
}
