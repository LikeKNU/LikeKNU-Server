package com.woopaca.univclub.club.controller.request;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record CreateOrUpdateClubRequest(@NotBlank @Length(max = 20) String name,
                                        @NotBlank String category,
                                        @NotBlank @Length(max = 8) String tag,
                                        @NotBlank @Length(min = 3, max = 3) String campus,
                                        @Length(max = 50) String recruitmentPeriod,
                                        @NotBlank @Length(max = 2_000) String introduction,
                                        @Length(max = 100) String membershipMethod,
                                        @Length(max = 30) String instagram,
                                        @Length(max = 1_000) String recruitmentUrl,
                                        @Length(max = 1_000) String youtubeUrl,
                                        @Length(max = 1_000) String homepageUrl,
                                        @Length(max = 100) String contact) {
}
