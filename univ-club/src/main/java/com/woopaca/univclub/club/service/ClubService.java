package com.woopaca.univclub.club.service;

import com.woopaca.univclub.club.controller.request.CreateOrUpdateClubRequest;
import com.woopaca.univclub.club.domain.Club;
import com.woopaca.univclub.club.repository.ClubRepository;
import com.woopaca.univclub.resource.ImageResource;
import com.woopaca.univclub.resource.storage.ResourceIdentifier;
import com.woopaca.univclub.resource.storage.ResourceKeyGenerators;
import com.woopaca.univclub.resource.storage.ResourceStorage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
public class ClubService {

    private final ClubRepository clubRepository;
    private final ResourceStorage resourceStorage;

    public ClubService(ClubRepository clubRepository, ResourceStorage resourceStorage) {
        this.clubRepository = clubRepository;
        this.resourceStorage = resourceStorage;
    }

    public void deleteClub(Long id) {
        Club club = clubRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("동아리 id 확인좀"));
        clubRepository.deleteById(id);
        deleteLogoIfPresent(club);
    }

    public void updateLogoImage(Long id, ImageResource imageResource) {
        Club club = clubRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("동아리 id 확인좀"));
        deleteLogoIfPresent(club);

        if (imageResource.isEmpty()) {
            clubRepository.updateLogoImage(null, id);
            return;
        }
        String imageUrl = resourceStorage.store(imageResource, ResourceKeyGenerators.LOGO_IMAGE_KEY_GENERATOR);
        clubRepository.updateLogoImage(imageUrl, id);
    }

    private void deleteLogoIfPresent(Club club) {
        String logoImageUrl = club.getLogoImageUrl();
        if (logoImageUrl != null) {
            resourceStorage.delete(ResourceIdentifier.of(logoImageUrl));
        }
    }

    public void updateClub(Long id, CreateOrUpdateClubRequest request) {
        Club club = clubRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("동아리 id 확인좀"));
        club.update(request);
    }
}
