package ac.knu.likeknu.controller;

import ac.knu.likeknu.controller.dto.announcement.AnnouncementListResponse;
import ac.knu.likeknu.controller.dto.base.ResponseDto;
import ac.knu.likeknu.controller.dto.device.request.BookmarkRequest;
import ac.knu.likeknu.service.BookmarkService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequestMapping("/api/bookmarks")
@RestController
public class BookmarkController {

    private final BookmarkService bookmarkService;

    public BookmarkController(BookmarkService bookmarkService) {
        this.bookmarkService = bookmarkService;
    }

    @PostMapping("/{deviceId}")
    public ResponseDto<String> createNewBookmark(
            @PathVariable("deviceId") String deviceId, @RequestBody BookmarkRequest request
    ) {
        bookmarkService.addAnnouncementBookmark(deviceId, request.announcementId());
        return ResponseDto.of("The bookmark has been created successfully.");
    }

    @DeleteMapping("/{deviceId}/{announcementId}")
    public ResponseDto<String> removeBookmark(
            @PathVariable("deviceId") String deviceId,
            @PathVariable("announcementId") String announcementId
    ) {
        bookmarkService.removeAnnouncementBookmark(deviceId, announcementId);
        return ResponseDto.of("The bookmark has been removed successfully.");
    }

    @GetMapping("/{deviceId}")
    public ResponseDto<List<AnnouncementListResponse>> bookmarkAnnouncements(
            @PathVariable("deviceId") String deviceId
    ) {
        List<AnnouncementListResponse> bookmarkAnnouncements = bookmarkService.getBookmarkAnnouncements(deviceId);
        return ResponseDto.of(bookmarkAnnouncements);
    }
}
