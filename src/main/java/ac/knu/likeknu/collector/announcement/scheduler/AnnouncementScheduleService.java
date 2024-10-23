package ac.knu.likeknu.collector.announcement.scheduler;

import ac.knu.likeknu.collector.announcement.dormitory.DormitoryAnnouncementClient;
import ac.knu.likeknu.collector.announcement.dormitory.DormitoryAnnouncementPageParser;
import ac.knu.likeknu.collector.announcement.dto.Announcement;
import ac.knu.likeknu.collector.announcement.dto.AnnouncementsMessage;
import ac.knu.likeknu.collector.announcement.library.LibraryAnnouncementClient;
import ac.knu.likeknu.collector.announcement.library.LibraryAnnouncementParser;
import ac.knu.likeknu.collector.announcement.studentnews.StudentNewsAnnouncementClient;
import ac.knu.likeknu.collector.announcement.studentnews.StudentNewsAnnouncementParser;
import ac.knu.likeknu.collector.menu.constants.Campus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class AnnouncementScheduleService {

    private final AnnouncementProducer announcementProducer;
    private final StudentNewsAnnouncementParser studentNewsAnnouncementParser;
    private final DormitoryAnnouncementClient dormitoryAnnouncementClient;
    private final DormitoryAnnouncementPageParser dormitoryAnnouncementPageParser;
    private final LibraryAnnouncementParser libraryAnnouncementParser;
    private final LibraryAnnouncementClient libraryAnnouncementClient;
    private final StudentNewsAnnouncementClient studentNewsAnnouncementClient;

    public AnnouncementScheduleService(AnnouncementProducer announcementProducer, StudentNewsAnnouncementParser studentNewsAnnouncementParser, DormitoryAnnouncementClient dormitoryAnnouncementClient, DormitoryAnnouncementPageParser dormitoryAnnouncementPageParser, LibraryAnnouncementParser libraryAnnouncementParser, LibraryAnnouncementClient libraryAnnouncementClient, StudentNewsAnnouncementClient studentNewsAnnouncementClient) {
        this.announcementProducer = announcementProducer;
        this.studentNewsAnnouncementParser = studentNewsAnnouncementParser;
        this.dormitoryAnnouncementClient = dormitoryAnnouncementClient;
        this.dormitoryAnnouncementPageParser = dormitoryAnnouncementPageParser;
        this.libraryAnnouncementParser = libraryAnnouncementParser;
        this.libraryAnnouncementClient = libraryAnnouncementClient;
        this.studentNewsAnnouncementClient = studentNewsAnnouncementClient;
    }

    @Scheduled(cron = "10 */10 6-23 * * ?")
    public void scheduleStudentNewsProduce() {
        String pageSource = studentNewsAnnouncementClient.fetchStudentNewsAnnouncementPage();
        List<Announcement> announcements = studentNewsAnnouncementParser.parseStudentNewsAnnouncementPage(pageSource);
        announcementProducer.produce(new AnnouncementsMessage(announcements));
    }

    @Scheduled(cron = "20 */10 6-23 * * ?")
    public void schedulingLibraryAnnouncementProduce() {
        String response = libraryAnnouncementClient.fetchLibraryAnnouncement();
        List<Announcement> announcements = libraryAnnouncementParser.parseLibraryAnnouncements(response);
        announcementProducer.produce(new AnnouncementsMessage(announcements));
    }

    @Scheduled(cron = "40 */10 6-23 * * ?")
    public void schedulingDormitoryAnnouncementProduce() {
        Arrays.stream(Campus.values())
                .filter(campus -> campus != Campus.ALL)
                .map(campus -> {
                    String pageSource = dormitoryAnnouncementClient.fetchDormitoryAnnouncementPage(campus, 1);
                    return dormitoryAnnouncementPageParser.parseDormitoryAnnouncementPage(pageSource, campus);
                })
                .map(AnnouncementsMessage::new)
                .forEach(announcementProducer::produce);
    }
}
