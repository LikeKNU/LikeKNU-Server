package ac.knu.likeknu.collector.announcement.scheduler;

import ac.knu.likeknu.collector.announcement.dormitory.DormitoryAnnouncementPageParser;
import ac.knu.likeknu.collector.announcement.dormitory.DormitoryAnnouncementRequestManager;
import ac.knu.likeknu.collector.announcement.dto.Announcement;
import ac.knu.likeknu.collector.announcement.library.LibraryNoticeManager;
import ac.knu.likeknu.collector.announcement.studentnews.StudentNewsManager;
import ac.knu.likeknu.collector.menu.constants.Campus;
import net.minidev.json.parser.ParseException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
public class AnnouncementScheduleService {

    private final AnnouncementProducer announcementProducer;
    private final StudentNewsManager studentNewsManager;
    private final DormitoryAnnouncementRequestManager dormitoryAnnouncementRequestManager;
    private final DormitoryAnnouncementPageParser dormitoryAnnouncementPageParser;
    private final LibraryNoticeManager libraryNoticeManager;

    public AnnouncementScheduleService(AnnouncementProducer announcementProducer, StudentNewsManager studentNewsManager, DormitoryAnnouncementRequestManager dormitoryAnnouncementRequestManager, DormitoryAnnouncementPageParser dormitoryAnnouncementPageParser, LibraryNoticeManager libraryNoticeManager) {
        this.announcementProducer = announcementProducer;
        this.studentNewsManager = studentNewsManager;
        this.dormitoryAnnouncementRequestManager = dormitoryAnnouncementRequestManager;
        this.dormitoryAnnouncementPageParser = dormitoryAnnouncementPageParser;
        this.libraryNoticeManager = libraryNoticeManager;
    }

    @Scheduled(cron = "10 */10 6-23 * * ?", zone = "Asia/Seoul")
    public void scheduleStudentNewsProduce() throws IOException {
        List<Announcement> announcements = studentNewsManager.fetchStudentNews();
        announcementProducer.produce(announcements);
    }

    @Scheduled(cron = "20 */10 6-23 * * ?")
    public void schedulingLibraryAnnouncementProduce() throws ParseException {
        List<Announcement> announcements = libraryNoticeManager.fetchLibraryNotice();
        announcementProducer.produce(announcements);
    }

    @Scheduled(cron = "40 */10 6-23 * * ?")
    public void schedulingDormitoryAnnouncementProduce() {
        Arrays.stream(Campus.values())
                .filter(campus -> campus != Campus.ALL)
                .map(campus -> {
                    String pageSource = dormitoryAnnouncementRequestManager.fetchDormitoryAnnouncementPage(campus, 1);
                    return dormitoryAnnouncementPageParser.parseDormitoryAnnouncementPage(pageSource, campus);
                })
                .forEach(announcementProducer::produce);
    }
}
