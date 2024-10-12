package ac.knu.likeknu.controller;

import ac.knu.likeknu.domain.ReleaseVersion;
import ac.knu.likeknu.repository.ReleaseVersionRepository;
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
