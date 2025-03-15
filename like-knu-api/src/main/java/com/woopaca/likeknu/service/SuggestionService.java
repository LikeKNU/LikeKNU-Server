package com.woopaca.likeknu.service;

import com.woopaca.likeknu.controller.dto.suggestion.request.SuggestionRequest;
import com.woopaca.likeknu.entity.Device;
import com.woopaca.likeknu.entity.Suggestion;
import com.woopaca.likeknu.exception.BusinessException;
import com.woopaca.likeknu.repository.DeviceRepository;
import com.woopaca.likeknu.repository.SuggestionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class SuggestionService {

    private final DeviceRepository deviceRepository;
    private final SuggestionRepository suggestionRepository;

    public SuggestionService(DeviceRepository deviceRepository, SuggestionRepository suggestionRepository) {
        this.deviceRepository = deviceRepository;
        this.suggestionRepository = suggestionRepository;
    }

    public void createNewSuggestion(SuggestionRequest request) {
        String deviceId = request.deviceId();
        Device device = deviceRepository.findById(deviceId)
                .orElseThrow(() -> new BusinessException(String.format("Device not found! [%s]", deviceId)));
        Suggestion suggestion = Suggestion.of(device, request.content());
        suggestionRepository.save(suggestion);
    }
}
