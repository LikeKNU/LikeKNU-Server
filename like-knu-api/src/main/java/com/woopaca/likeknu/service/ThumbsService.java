package com.woopaca.likeknu.service;

import com.woopaca.likeknu.ThumbsType;
import com.woopaca.likeknu.controller.dto.menu.MenuThumbsStatusResponse;
import com.woopaca.likeknu.entity.Device;
import com.woopaca.likeknu.entity.Menu;
import com.woopaca.likeknu.entity.MenuThumbs;
import com.woopaca.likeknu.exception.BusinessException;
import com.woopaca.likeknu.repository.DeviceRepository;
import com.woopaca.likeknu.repository.MenuRepository;
import com.woopaca.likeknu.repository.MenuThumbsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ThumbsService {

    private final MenuThumbsRepository menuThumbsRepository;
    private final DeviceRepository deviceRepository;
    private final MenuRepository menuRepository;

    public ThumbsService(MenuThumbsRepository menuThumbsRepository, DeviceRepository deviceRepository, MenuRepository menuRepository) {
        this.menuThumbsRepository = menuThumbsRepository;
        this.deviceRepository = deviceRepository;
        this.menuRepository = menuRepository;
    }

    public MenuThumbsStatusResponse getMenuThumbsStatus(String menuId, String deviceId) {
        Device device = deviceRepository.findById(deviceId)
                .orElseThrow(() -> new BusinessException(String.format("device does not exist [%s]", deviceId)));
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new BusinessException(String.format("menu does not exist [%s]", menuId)));

        List<MenuThumbs> thumbsList = menuThumbsRepository.findByMenu(menu);
        Map<ThumbsType, List<MenuThumbs>> thumbsGroup = thumbsList.stream()
                .collect(Collectors.groupingBy(MenuThumbs::getThumbsType));

        int thumbsUpCount = getThumbsCount(thumbsGroup, ThumbsType.THUMBS_UP);
        int thumbsDownCount = getThumbsCount(thumbsGroup, ThumbsType.THUMBS_DOWN);

        String ownThumbs = findOwnThumbsType(thumbsList, device);
        return new MenuThumbsStatusResponse(thumbsUpCount, thumbsDownCount, ownThumbs);
    }

    private int getThumbsCount(Map<ThumbsType, List<MenuThumbs>> thumbsGroup, ThumbsType thumbsUp) {
        return thumbsGroup
                .getOrDefault(thumbsUp, new ArrayList<>())
                .size();
    }

    private String findOwnThumbsType(List<MenuThumbs> thumbsList, Device device) {
        return thumbsList.stream()
                .filter(thumbs -> thumbs.getDevice().equals(device))
                .findAny()
                .map(MenuThumbs::getType)
                .orElse(null);
    }

    @Transactional
    public MenuThumbsStatusResponse updateThumbs(String menuId, String deviceId, ThumbsType thumbsType) {
        Device device = deviceRepository.findById(deviceId)
                .orElseThrow(() -> new BusinessException(String.format("device does not exist [%s]", deviceId)));
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new BusinessException(String.format("menu does not exist [%s]", menuId)));

        Optional<MenuThumbs> ownThumbs = menuThumbsRepository.findByMenuAndDevice(menu, device);
        ownThumbs.ifPresentOrElse(thumbs -> changeThumbs(thumbs, thumbsType), () -> createThumbs(menu, device, thumbsType));
        return getMenuThumbsStatus(menuId, deviceId);
    }

    private void changeThumbs(MenuThumbs thumbs, ThumbsType thumbsType) {
        if (thumbs.isTypeOf(thumbsType)) {
            menuThumbsRepository.delete(thumbs);
            return;
        }
        thumbs.changeType(thumbsType);
    }

    private void createThumbs(Menu menu, Device device, ThumbsType thumbsType) {
        MenuThumbs menuThumbs = MenuThumbs.builder()
                .thumbsType(thumbsType)
                .menu(menu)
                .device(device)
                .build();
        menuThumbsRepository.save(menuThumbs);
    }
}
