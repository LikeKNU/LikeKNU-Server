package ac.knu.likeknu.controller;

import ac.knu.likeknu.controller.dto.base.ResponseDto;
import ac.knu.likeknu.controller.dto.device.request.CampusModificationRequest;
import ac.knu.likeknu.controller.dto.device.request.DeviceRegistrationRequest;
import ac.knu.likeknu.service.DeviceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/devices")
@RequiredArgsConstructor
public class DeviceController {

    private final DeviceService deviceService;

    @PostMapping
    public ResponseDto<String> registerDevice(@RequestBody DeviceRegistrationRequest request) {
        deviceService.registerDeviceId(request);
        return ResponseDto.of("The device was successfully registered.");
    }

    @PutMapping("/campus")
    public ResponseDto<String> modifyCampus(@RequestBody CampusModificationRequest request) {
        deviceService.modifyCampusByDeviceId(request);
        return ResponseDto.of("Campus has been changed successfully.");
    }

}
