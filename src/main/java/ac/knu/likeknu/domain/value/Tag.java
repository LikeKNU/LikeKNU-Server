package ac.knu.likeknu.domain.value;

import lombok.Getter;

@Getter
public enum Tag {

    ENROLMENT("수강신청"),
    DORMITORY("생활관"),
    SCHOLARSHIP("장학금"),
    TUITION("등록금"),
    WORK("근로"),
    LIBRARY("도서관"),
    MILEAGE("마일리지"),
    INTERNSHIP("현장실습"),
    ETC("기타");

    private final String tagName;

    Tag(String tagName) {
        this.tagName = tagName;
    }
}
