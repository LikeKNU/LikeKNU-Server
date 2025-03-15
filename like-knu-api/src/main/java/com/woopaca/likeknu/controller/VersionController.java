package com.woopaca.likeknu.controller;

import com.woopaca.likeknu.entity.ReleaseVersion;
import com.woopaca.likeknu.repository.ReleaseVersionRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/versions")
@RestController
public class VersionController {

    private final ReleaseVersionRepository releaseVersionRepository;

    public VersionController(ReleaseVersionRepository releaseVersionRepository) {
        this.releaseVersionRepository = releaseVersionRepository;
    }

    @GetMapping
    public String getVersion() {
        ReleaseVersion latestVersion = releaseVersionRepository.findLatestVersion();
        return latestVersion.getVersion();
    }
}
