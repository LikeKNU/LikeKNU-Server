package ac.knu.likeknu.controller;

import ac.knu.likeknu.controller.dto.base.ResponseDto;
import ac.knu.likeknu.controller.dto.device.request.DeviceRegistrationRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/devices")
public class DeviceController {

    @PostMapping
    public ResponseDto<String> deviceRegistration(@RequestBody DeviceRegistrationRequest request) {
        return ResponseDto.of("You have successfully registered your device.");
    }

}
