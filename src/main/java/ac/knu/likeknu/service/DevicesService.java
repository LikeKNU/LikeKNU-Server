package ac.knu.likeknu.service;

import ac.knu.likeknu.controller.dto.device.SubscribeTagListResponse;
import ac.knu.likeknu.domain.Device;
import ac.knu.likeknu.exception.BusinessException;
import ac.knu.likeknu.repository.DevicesRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@Service
public class DevicesService {

    private final DevicesRepository devicesRepository;

    public DevicesService(DevicesRepository devicesRepository) {
        this.devicesRepository = devicesRepository;
    }

    public List<SubscribeTagListResponse> getSubscribeTagList(String deviceId) {
        Device device = devicesRepository.findById(deviceId)
                .orElseThrow(() -> new BusinessException(String.format("Device not found! [%s]", deviceId)));
        return device.getSubscribeTags().stream()
                .map(SubscribeTagListResponse::of)
                .toList();
    }
}
