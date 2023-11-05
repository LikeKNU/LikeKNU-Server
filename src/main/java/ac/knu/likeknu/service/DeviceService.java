package ac.knu.likeknu.service;

import ac.knu.likeknu.controller.dto.device.request.DeviceRegistrationRequest;
import ac.knu.likeknu.domain.Device;
import ac.knu.likeknu.repository.DeviceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DeviceService {

    private final DeviceRepository deviceRepository;

    @Transactional
    public String registerDeviceId(DeviceRegistrationRequest deviceRequest) {
        if (!IsDeviceIdExist(deviceRequest.getDeviceId())) {
            deviceRepository.save(Device.of(deviceRequest));
            return "You have successfully registered your device.";
        }

        return "The device is already registered.";
    }

    private boolean IsDeviceIdExist(String deviceId) {
        return deviceRepository.findById(deviceId).isPresent();
    }
}
