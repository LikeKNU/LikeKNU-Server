package com.woopaca.likeknu.controller;

import com.woopaca.likeknu.Campus;
import com.woopaca.likeknu.controller.dto.base.ResponseDto;
import com.woopaca.likeknu.controller.dto.menu.CafeteriaMealListResponse;
import com.woopaca.likeknu.controller.dto.menu.MenuThumbsRequest;
import com.woopaca.likeknu.controller.dto.menu.MenuThumbsStatusResponse;
import com.woopaca.likeknu.exception.BusinessException;
import com.woopaca.likeknu.service.MenuService;
import com.woopaca.likeknu.service.ThumbsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/menus")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;
    private final ThumbsService thumbsService;

    @GetMapping
    public ResponseDto<List<CafeteriaMealListResponse>> getMenuByCampus(
            @RequestParam(name = "campus") Campus campus,
            @RequestParam(name = "cafeteriaName") String cafeteriaName
    ) {
        if (campus.equals(Campus.ALL)) {
            throw new BusinessException("Invalid campus");
        }

        List<CafeteriaMealListResponse> cafeteriaMeals = menuService.getCafeteriaMeals(campus, cafeteriaName);
        return ResponseDto.of(cafeteriaMeals);
    }

    @GetMapping("/{menuId}/thumbs")
    public ResponseDto<MenuThumbsStatusResponse> getThumbsStatus(
            @PathVariable(name = "menuId") String menuId,
            @RequestParam(name = "deviceId") String deviceId
    ) {
        MenuThumbsStatusResponse menuThumbsStatus = thumbsService.getMenuThumbsStatus(menuId, deviceId);
        return ResponseDto.of(menuThumbsStatus);
    }

    @PutMapping("/{menuId}/thumbs")
    public ResponseDto<MenuThumbsStatusResponse> updateThumbs(
            @PathVariable(name = "menuId") String menuId,
            @RequestBody MenuThumbsRequest menuThumbsRequest
    ) {
        MenuThumbsStatusResponse updatedMenuThumbsStatus =
                thumbsService.updateThumbs(menuId, menuThumbsRequest.deviceId(), menuThumbsRequest.thumbsType());
        return ResponseDto.of(updatedMenuThumbsStatus);
    }
}
