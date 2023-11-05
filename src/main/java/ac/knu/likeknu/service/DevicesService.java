package ac.knu.likeknu.service;

import ac.knu.likeknu.controller.dto.device.SubscribeTagListResponse;
import ac.knu.likeknu.controller.dto.device.SubscribeTagsUpdateRequest;
import ac.knu.likeknu.controller.dto.device.TagName;
import ac.knu.likeknu.domain.Device;
import ac.knu.likeknu.domain.value.Tag;
import ac.knu.likeknu.exception.BusinessException;
import ac.knu.likeknu.repository.DevicesRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@Service
public class DevicesService {

    private final DevicesRepository devicesRepository;

    public DevicesService(DevicesRepository devicesRepository) {
        this.devicesRepository = devicesRepository;
    }

    public List<SubscribeTagListResponse> getSubscribeTagList(String deviceId) {
        Device device = devicesRepository.findById(deviceId)
                .orElseThrow(() -> new BusinessException(String.format("Device not found! [%s]", deviceId)));
        return device.getSubscribeTags().stream()
                .map(SubscribeTagListResponse::of)
                .toList();
    }

    @Transactional
    public void updateSubscribeTagList(SubscribeTagsUpdateRequest subscribeTagsUpdateRequest) {
        String deviceId = subscribeTagsUpdateRequest.deviceId();
        List<TagName> tagNames = subscribeTagsUpdateRequest.tags();
        List<Tag> tags = convertToTags(tagNames);
        Device device = devicesRepository.findById(deviceId)
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
}
