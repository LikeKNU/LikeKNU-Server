package ac.knu.likeknu.logging.domain.value;

public enum LogType {

    SEARCH_ANNOUNCEMENT("공지사항 검색"),
    SELECT_SHUTTLE("셔틀 선택");

    private final String typeName;

    LogType(String typeName) {
        this.typeName = typeName;
    }
}
