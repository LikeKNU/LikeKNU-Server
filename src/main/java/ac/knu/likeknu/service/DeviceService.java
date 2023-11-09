package ac.knu.likeknu.service;

import ac.knu.likeknu.controller.dto.device.request.CampusModificationRequest;
import ac.knu.likeknu.controller.dto.device.request.DeviceRegistrationRequest;
import ac.knu.likeknu.domain.Device;
import ac.knu.likeknu.domain.value.Campus;
import ac.knu.likeknu.exception.BusinessException;
import ac.knu.likeknu.repository.DeviceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class DeviceService {

    private final DeviceRepository deviceRepository;

    public void registerDeviceId(DeviceRegistrationRequest deviceRequest) {
        Device device = Device.of(deviceRequest);
        deviceRepository.save(device);
    }

    public void modifyCampusByDeviceId(CampusModificationRequest campusModificationRequest) {
        String deviceId = campusModificationRequest.getDeviceId();
        Device device = deviceRepository.findById(deviceId)
                .orElseThrow(() -> new BusinessException("deviceId does not exist."));

        device.setCampus(Campus.valueOf(campusModificationRequest.getCampus()));
    }

}
