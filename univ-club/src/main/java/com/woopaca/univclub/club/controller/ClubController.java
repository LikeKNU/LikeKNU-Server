package com.woopaca.univclub.club.controller;

import com.woopaca.univclub.club.controller.request.CreateOrUpdateClubRequest;
import com.woopaca.univclub.club.controller.response.ClubDetailsResponse;
import com.woopaca.univclub.club.controller.response.ClubIdResponse;
import com.woopaca.univclub.club.controller.response.ClubsResponse;
import com.woopaca.univclub.club.domain.Campus;
import com.woopaca.univclub.club.domain.Category;
import com.woopaca.univclub.club.domain.Club;
import com.woopaca.univclub.club.repository.ClubRepository;
import com.woopaca.univclub.club.service.ClubService;
import com.woopaca.univclub.resource.UniqueRenameAbstractImageResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RequestMapping("/api/v1/clubs")
@RestController
public class ClubController {

    private final ClubRepository clubRepository;
    private final ClubService clubService;

    public ClubController(ClubRepository clubRepository, ClubService clubService) {
        this.clubRepository = clubRepository;
        this.clubService = clubService;
    }

    @GetMapping
    public List<ClubsResponse> clubList() {
        return clubRepository.findAll()
                .stream()
                .map(ClubsResponse::from)
                .toList();
    }

    @GetMapping("/{id}")
    public ClubDetailsResponse clubDetails(@PathVariable("id") Long id) {
        return clubRepository.findById(id)
                .map(ClubDetailsResponse::from)
                .orElseThrow(() -> new IllegalArgumentException("동아리 id 확인좀"));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ClubIdResponse createClub(@Validated @RequestBody CreateOrUpdateClubRequest request) {
        Club newClub = Club.builder()
                .name(request.name())
                .campus(Campus.ofName(request.campus()))
                .category(Category.ofName(request.category()))
                .tag(request.tag())
                .instagram(request.instagram())
                .recruitmentPeriod(request.recruitmentPeriod())
                .introduction(request.introduction())
                .membershipMethod(request.membershipMethod())
                .recruitmentUrl(request.recruitmentUrl())
                .contact(request.contact())
                .youtubeUrl(request.youtubeUrl())
                .homepageUrl(request.homepageUrl())
                .build();
        Club club = clubRepository.save(newClub);
        return new ClubIdResponse(club.getId());
    }

    @PutMapping("/{id}")
    public ClubIdResponse updateClub(@PathVariable("id") Long id, @Validated @RequestBody CreateOrUpdateClubRequest request) {
        clubService.updateClub(id, request);
        return new ClubIdResponse(id);
    }

    @DeleteMapping("/{id}")
    public ClubIdResponse deleteClub(@PathVariable("id") Long id) {
        clubService.deleteClub(id);
        return new ClubIdResponse(id);
    }

    @PutMapping("/{id}/logo")
    public ClubIdResponse updateLogo(@PathVariable("id") Long id,
                                     @RequestPart(value = "logoImage", required = false) MultipartFile multipartFile) {
        clubService.updateLogoImage(id, UniqueRenameAbstractImageResource.from(multipartFile));
        return new ClubIdResponse(id);
    }
}
