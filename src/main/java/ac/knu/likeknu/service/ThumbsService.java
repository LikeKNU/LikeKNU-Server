package ac.knu.likeknu.service;

import ac.knu.likeknu.controller.dto.menu.MenuThumbsStatusResponse;
import ac.knu.likeknu.domain.Device;
import ac.knu.likeknu.domain.Menu;
import ac.knu.likeknu.domain.MenuThumbs;
import ac.knu.likeknu.domain.constants.ThumbsType;
import ac.knu.likeknu.exception.BusinessException;
import ac.knu.likeknu.repository.DeviceRepository;
import ac.knu.likeknu.repository.MenuRepository;
import ac.knu.likeknu.repository.MenuThumbsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Transactional
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
}
