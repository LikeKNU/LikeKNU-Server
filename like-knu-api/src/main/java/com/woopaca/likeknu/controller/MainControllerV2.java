package com.woopaca.likeknu.controller;

import com.woopaca.likeknu.Campus;
import com.woopaca.likeknu.controller.dto.base.ResponseDto;
import com.woopaca.likeknu.controller.dto.menu.MainMenuResponseV2;
import com.woopaca.likeknu.exception.BusinessException;
import com.woopaca.likeknu.service.MainService;
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
