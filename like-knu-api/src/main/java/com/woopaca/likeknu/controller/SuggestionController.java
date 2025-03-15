package com.woopaca.likeknu.controller;

import com.woopaca.likeknu.controller.dto.base.ResponseDto;
import com.woopaca.likeknu.controller.dto.suggestion.request.SuggestionRequest;
import com.woopaca.likeknu.service.SuggestionService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/suggestions")
@RestController
public class SuggestionController {

    private final SuggestionService suggestionService;

    public SuggestionController(SuggestionService suggestionService) {
        this.suggestionService = suggestionService;
    }

    @PostMapping
    public ResponseDto<String> registerSuggestion(@RequestBody @Valid SuggestionRequest request) {
        suggestionService.createNewSuggestion(request);
        return ResponseDto.of("Your offer has been registered.");
    }
}
