package ac.knu.likeknu.controller;

import ac.knu.likeknu.controller.dto.base.ResponseDto;
import ac.knu.likeknu.controller.dto.device.request.CampusModificationRequest;
import ac.knu.likeknu.controller.dto.device.request.DeviceRegistrationRequest;
import ac.knu.likeknu.service.DeviceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/devices")
@RequiredArgsConstructor
public class DeviceController {

    private final DeviceService deviceService;

    @PostMapping
    public ResponseDto<String> registerDevice(@RequestBody DeviceRegistrationRequest request) {
        ResponseEntity<String> responseEntity = deviceService.registerDeviceId(request);
        return ResponseDto.of(responseEntity.getBody());
    }

    @PutMapping("/campus")
    public ResponseDto<String> modifyCampus(@RequestBody CampusModificationRequest request) {
        return ResponseDto.of("Campus has been changed successfully.");
    }

}
