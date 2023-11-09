package ac.knu.likeknu.controller;

import ac.knu.likeknu.controller.dto.base.ResponseDto;
import ac.knu.likeknu.controller.dto.device.SubscribeTagListResponse;
import ac.knu.likeknu.controller.dto.device.SubscribeTagsUpdateRequest;
import ac.knu.likeknu.service.DevicesService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api/devices")
@RestController
public class DevicesController {

    private final DevicesService devicesService;

    public DevicesController(DevicesService devicesService) {
        this.devicesService = devicesService;
    }

    @GetMapping("/subscribes")
    public ResponseDto<List<SubscribeTagListResponse>> deviceSubscribeTagList(
            @RequestParam("deviceId") String deviceId
    ) {
        List<SubscribeTagListResponse> subscribeTagList = devicesService.getSubscribeTagList(deviceId);
        return ResponseDto.of(subscribeTagList);
    }

    @PutMapping("/subscribes")
    public ResponseDto<String> updateDeviceSubscribeTagList(
            @RequestBody SubscribeTagsUpdateRequest subscribeTagsUpdateRequest
    ) {
        devicesService.updateSubscribeTagList(subscribeTagsUpdateRequest);
        return ResponseDto.of(null);
    }
}
