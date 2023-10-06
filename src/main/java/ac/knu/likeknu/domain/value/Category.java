package ac.knu.likeknu.domain.value;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum Category {

    STUDENT_NEWS("학생소식", "student-news"),
    LIBRARY("도서관", "library"),
    DORMITORY("생활관", "dormitory"),
    INTERNSHIP("현장실습", "internship");

    private final String categoryName;
    private final String pathVariable;

    Category(String categoryName, String pathVariable) {
        this.categoryName = categoryName;
        this.pathVariable = pathVariable;
    }

    public static Category of(String category) {
        return Arrays.stream(Category.values())
                .filter(it -> it.pathVariable.equals(category))
                .findFirst()
                .orElseThrow();
    }
}
