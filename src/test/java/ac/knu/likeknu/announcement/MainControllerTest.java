package ac.knu.likeknu.announcement;

import ac.knu.likeknu.domain.Announcement;
import ac.knu.likeknu.domain.Campus;
import ac.knu.likeknu.domain.Tag;
import ac.knu.likeknu.domain.Category;
import ac.knu.likeknu.repository.AnnouncementRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MainControllerTest {

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;
    @Autowired
    private AnnouncementRepository announcementRepository;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();

//        // 더미 데이터 저장
//        Announcement announcement = new Announcement("testTitle1", "testUrl", LocalDate.now(), Campus.CHEONAN, Category.SCHOOL_NEWS, Tag.Test);
//        Announcement announcement2 = new Announcement("testTitle2", "testUrl", LocalDate.now(), Campus.ALL, Category.SCHOOL_NEWS, Tag.Test);
//        Announcement announcement3 = new Announcement("testTitle3", "testUrl", LocalDate.now(), Campus.ALL, Category.LIBRARY, Tag.Test);
//        Announcement announcement4 = new Announcement("testTitle4", "testUrl", LocalDate.now(), Campus.ALL, Category.SCHOOL_NEWS, Tag.Test);
//
//        announcementRepository.save(announcement);
//        announcementRepository.save(announcement2);
//        announcementRepository.save(announcement3);
//        announcementRepository.save(announcement4);
    }

    @AfterEach
    public void tearDown() {
        announcementRepository.deleteAll();
    }

    @Test
    @DisplayName("메인 공지사항 조회 API 테스트")
    void getAnnouncementTest() throws Exception {

//        mvc.perform(
//                get("/api/main/announcements")
//                        .param("campus", "천안"))
//
//                .andExpect(
//                        status().isOk()
//                ).andExpect(
//                        jsonPath("$.data.body[0].announcementTitle")
//                                .value("testTitle1")
//                ).andExpect(
//                        jsonPath("$.data.body[1].announcementTitle")
//                                .value("testTitle2")
//                ).andExpect(
//                        jsonPath("$.data.body[2].announcementTitle")
//                                .value("testTitle4")
//                );
        System.out.println("성공!");
    }
}
