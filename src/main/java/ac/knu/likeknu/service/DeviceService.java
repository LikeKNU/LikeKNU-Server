package ac.knu.likeknu.service;

import ac.knu.likeknu.controller.dto.device.request.CampusModificationRequest;
import ac.knu.likeknu.controller.dto.device.request.DeviceRegistrationRequest;
import ac.knu.likeknu.controller.dto.device.request.DeviceTokenRequest;
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
        String deviceId = campusModificationRequest.deviceId();
        Device device = deviceRepository.findById(deviceId)
                .orElseThrow(() -> new BusinessException(String.format("deviceId: %s does not exist.", deviceId)));

        device.setCampus(Campus.valueOf(campusModificationRequest.campus()));
    }

    public void registerTokenByDevice(DeviceTokenRequest tokenRequest) {
        String deviceId = tokenRequest.deviceId();
        Device device = deviceRepository.findById(deviceId)
                .orElseThrow(() -> new BusinessException(String.format("deviceId: %s does not exist.", deviceId)));
        device.setFcmToken(tokenRequest.token());
    }

}
