package com.woopaca.univclub.club.domain;

import com.woopaca.univclub.club.controller.request.CreateOrUpdateClubRequest;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@DynamicUpdate
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Club {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private Campus campus;

    @Enumerated(EnumType.STRING)
    private Category category;

    private String tag;

    private String contact;

    private String instagram;

    private String recruitmentPeriod;

    private String logoImageUrl;

    private String introduction;

    private String introductionImageUrl;

    private String membershipMethod;

    private String recruitmentUrl;

    private String youtubeUrl;

    private String homepageUrl;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public Club() {
    }

    @Builder
    public Club(Long id, String name, Campus campus, Category category, String tag, String contact, String instagram, String recruitmentPeriod, String logoImageUrl, String introduction, String introductionImageUrl, String membershipMethod, String recruitmentUrl, String youtubeUrl, String homepageUrl) {
        this.id = id;
        this.name = name;
        this.campus = campus;
        this.category = category;
        this.tag = tag;
        this.contact = contact;
        this.instagram = instagram;
        this.recruitmentPeriod = recruitmentPeriod;
        this.logoImageUrl = logoImageUrl;
        this.introduction = introduction;
        this.introductionImageUrl = introductionImageUrl;
        this.membershipMethod = membershipMethod;
        this.recruitmentUrl = recruitmentUrl;
        this.youtubeUrl = youtubeUrl;
        this.homepageUrl = homepageUrl;
    }

    public void update(CreateOrUpdateClubRequest request) {
        this.name = request.name();
        this.campus = Campus.ofName(request.campus());
        this.category = Category.ofName(request.category());
        this.tag = request.tag();
        this.contact = request.contact();
        this.instagram = request.instagram();
        this.recruitmentPeriod = request.recruitmentPeriod();
        this.introduction = request.introduction();
        this.membershipMethod = request.membershipMethod();
        this.recruitmentUrl = request.recruitmentUrl();
        this.youtubeUrl = request.youtubeUrl();
        this.homepageUrl = request.homepageUrl();
    }

    public Club withGeneratedId(long generatedId) {
        this.id = generatedId;
        return this;
    }
}
