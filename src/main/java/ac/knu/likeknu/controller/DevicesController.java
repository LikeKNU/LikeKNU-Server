package ac.knu.likeknu.controller;

import ac.knu.likeknu.controller.dto.base.ResponseDto;
import ac.knu.likeknu.controller.dto.device.SubscribeTagListResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RequestMapping("/api/devices")
@RestController
public class DevicesController {

    @GetMapping("/subscribes")
    public ResponseDto<List<SubscribeTagListResponse>> deviceSubscribeTagList(
            @RequestParam("deviceId") String deviceId
    ) {
        return ResponseDto.of(Collections.emptyList());
    }
}
