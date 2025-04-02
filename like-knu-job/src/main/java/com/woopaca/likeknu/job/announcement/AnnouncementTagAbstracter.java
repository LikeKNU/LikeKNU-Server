package com.woopaca.likeknu.job.announcement;

import com.woopaca.likeknu.Tag;
import com.woopaca.likeknu.job.announcement.dto.AnnouncementMessage;

public interface AnnouncementTagAbstracter {

    Tag abstractTag(AnnouncementMessage announcementMessage);
}
