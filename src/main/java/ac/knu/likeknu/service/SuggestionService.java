package ac.knu.likeknu.service;

import ac.knu.likeknu.controller.dto.suggestion.request.SuggestionRequest;
import ac.knu.likeknu.domain.Device;
import ac.knu.likeknu.domain.Suggestion;
import ac.knu.likeknu.exception.BusinessException;
import ac.knu.likeknu.repository.DeviceRepository;
import ac.knu.likeknu.repository.SuggestionRepository;
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
