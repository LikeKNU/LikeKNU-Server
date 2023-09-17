package ac.knu.likeknu.domain;

import lombok.Getter;

@Getter
public enum Tag {

    SCHOOL_NEWS("학생소식"),
    LIBRARY("도서관"),
    DORMITORY("생활관"),
    TALENT_DEVELOPMENT("인재개발");

    private String dept;

    Tag(String dept) {
        this.dept = dept;
    }
}
