package ac.knu.likeknu.controller;

import ac.knu.likeknu.controller.dto.announcement.AnnouncementListResponse;
import ac.knu.likeknu.controller.dto.base.PageDto;
import ac.knu.likeknu.domain.Announcement;
import ac.knu.likeknu.domain.constants.Campus;
import ac.knu.likeknu.domain.constants.Category;
import ac.knu.likeknu.domain.constants.Tag;
import ac.knu.likeknu.logging.service.LoggingService;
import ac.knu.likeknu.service.AnnouncementService;
import ac.knu.likeknu.service.SlackService;
import ac.knu.likeknu.utils.TestInstanceFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@WithMockUser
@DisplayName("공지사항 컨트롤러 테스트")
@WebMvcTest(controllers = AnnouncementController.class)
class AnnouncementControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AnnouncementService announcementService;
    @MockBean
    private SlackService slackService;
    @MockBean
    private LoggingService loggingService;

    @DisplayName("학생소식 조회 API 요청에 성공한다.")
    @Test
    void fetchStudentNewsApiSuccess() throws Exception {
        // given
        Announcement announcement1 = TestInstanceFactory.createAnnouncement("Test A", "https://testa.com", Tag.DORMITORY);
        Announcement announcement2 = TestInstanceFactory.createAnnouncement("Test B", "https://testb.com", Tag.ENROLMENT);
        Announcement announcement3 = TestInstanceFactory.createAnnouncement("Test C", "https://testc.com", Tag.LIBRARY);

        // when
        when(announcementService.getAnnouncements(eq(Campus.CHEONAN), eq(Category.STUDENT_NEWS), any(PageDto.class), any(), nativeDeviceId))
                .thenReturn(List.of(
                        AnnouncementListResponse.of(announcement1),
                        AnnouncementListResponse.of(announcement2),
                        AnnouncementListResponse.of(announcement3)
                ));

        ResultActions resultActions =
                mockMvc.perform(get("/api/announcements/{category}", "student-news")
                        .param("campus", Campus.CHEONAN.name()));

        // then
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        resultActions.andExpectAll(
                status().isOk(),
                jsonPath("$.data.body.[0].announcementTitle").value(announcement1.getAnnouncementTitle()),
                jsonPath("$.data.body.[0].announcementUrl").value(announcement1.getAnnouncementUrl()),
                jsonPath("$.data.body.[0].announcementDate").value(announcement1.getAnnouncementDate().format(dateTimeFormatter)),
                jsonPath("$.data.body.[0].announcementTag").value(announcement1.getTag().getTagName()),
                jsonPath("$.data.page.currentPage").value(1)
        ).andDo(print());
    }

    @DisplayName("유효하지 않은 페이지 요청에 실패한다.")
    @Test
    void fetchStudentNewsApiFailInvalidPage() throws Exception {
        // given

        // when
        ResultActions resultActions =
                mockMvc.perform(get("/api/announcements/{category}", Category.STUDENT_NEWS.getPathVariable())
                        .param("campus", Campus.CHEONAN.name())
                        .param("page", "0"));

        // then
        resultActions.andExpectAll(
                status().isBadRequest()
        ).andDo(print());
    }
}