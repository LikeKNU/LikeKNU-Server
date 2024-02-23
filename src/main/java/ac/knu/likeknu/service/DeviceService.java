package ac.knu.likeknu.service;

import ac.knu.likeknu.controller.dto.device.request.CampusModificationRequest;
import ac.knu.likeknu.controller.dto.device.request.ChangeNotificationRequest;
import ac.knu.likeknu.controller.dto.device.request.DeviceRegistrationRequest;
import ac.knu.likeknu.controller.dto.device.request.DeviceTokenRequest;
import ac.knu.likeknu.controller.dto.device.request.SubscribeTagsUpdateRequest;
import ac.knu.likeknu.controller.dto.device.request.TagName;
import ac.knu.likeknu.controller.dto.device.response.SubscribeTagListResponse;
import ac.knu.likeknu.domain.Device;
import ac.knu.likeknu.domain.value.Campus;
import ac.knu.likeknu.domain.value.Tag;
import ac.knu.likeknu.exception.BusinessException;
import ac.knu.likeknu.repository.DeviceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Transactional
@Service
public class DeviceService {

    private final DeviceRepository deviceRepository;
    private final Set<String> registeredDevices;

    public DeviceService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
        this.registeredDevices = Collections.synchronizedSet(new HashSet<>());
    }

    public void registerDeviceId(DeviceRegistrationRequest deviceRequest) {
        String userAgent = deviceRequest.userAgent();
        if (userAgent.contains("Googlebot") || userAgent.contains("AdsBot")) {
            return;
        }

        String deviceId = deviceRequest.deviceId();
        Device device = deviceRepository.findById(deviceId)
                .orElseGet(() -> Device.of(deviceRequest));

        device.updatePlatform(userAgent);
        String campus = deviceRequest.campus();
        device.updateCampus(Campus.of(campus));
        device.visitNow();
        if (registeredDevices.add(deviceId)) {
            deviceRepository.save(device);
        }
    }

    public void modifyCampusByDeviceId(CampusModificationRequest campusModificationRequest) {
        String deviceId = campusModificationRequest.deviceId();
        Device device = deviceRepository.findById(deviceId)
                .orElseThrow(() -> new BusinessException(String.format("deviceId: %s does not exist.", deviceId)));

        try {
            Campus campus = Campus.valueOf(campusModificationRequest.campus());
            device.setCampus(campus);
        } catch (IllegalArgumentException exception) {
            throw new BusinessException("Invalid campus");
        }
    }

    public void registerTokenByDevice(DeviceTokenRequest tokenRequest) {
        String deviceId = tokenRequest.deviceId();
        Device device = deviceRepository.findById(deviceId)
                .orElseThrow(() -> new BusinessException(String.format("deviceId: %s does not exist.", deviceId)));
        device.setFcmToken(tokenRequest.token());
    }

    public List<SubscribeTagListResponse> getSubscribeTagList(String deviceId) {
        Device device = deviceRepository.findById(deviceId)
                .orElseThrow(() -> new BusinessException(String.format("Device not found! [%s]", deviceId)));
        return device.getSubscribeTags().stream()
                .map(SubscribeTagListResponse::of)
                .toList();
    }

    public void updateSubscribeTagList(SubscribeTagsUpdateRequest subscribeTagsUpdateRequest) {
        String deviceId = subscribeTagsUpdateRequest.deviceId();
        List<TagName> tagNames = subscribeTagsUpdateRequest.tags();
        List<Tag> tags = convertToTags(tagNames);
        Device device = deviceRepository.findById(deviceId)
                .orElseThrow(() -> new BusinessException(String.format("Device not found! [%s]", deviceId)));
        device.updateSubscribesTags(tags);
    }

    private List<Tag> convertToTags(List<TagName> tagNames) {
        return tagNames.stream()
                .distinct()
                .map(TagName::tag)
                .map(Tag::of)
                .toList();
    }

    @Transactional(readOnly = true)
    public boolean isTurnOnPushNotifications(String deviceId) {
        Device device = deviceRepository.findById(deviceId)
                .orElseThrow(() -> new BusinessException(String.format("Device not found! [%s]", deviceId)));
        return device.isTurnOnNotification();
    }

    public void changeDeviceNotifications(ChangeNotificationRequest request) {
        String deviceId = request.deviceId();
        Device device = deviceRepository.findById(deviceId)
                .orElseThrow(() -> new BusinessException(String.format("Device not found! [%s]", deviceId)));

        device.updateNotification(request.notification());
    }
}
