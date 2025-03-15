package com.woopaca.likeknu.controller.dto.device.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.woopaca.likeknu.Campus;
import com.woopaca.likeknu.entity.Device;

import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DeviceRegistrationRequest(String deviceId, String platform, String modelName, String osVersion,
                                        String appVersion, Campus campus, String themeColor, String favoriteCafeteria,
                                        String expoPushToken) {

    public Device toEntity() {
        return Device.builder()
                .id(deviceId)
                .campus(Campus.SINGWAN)
                .registeredAt(LocalDateTime.now())
                .build();
    }
}
