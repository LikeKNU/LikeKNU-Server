package ac.knu.likeknu.collector.menu.constants;

import lombok.Getter;

@Getter
public enum DormitoryCafeteria implements CafeteriaProxy {

    SINGWAN_EANHANGSA(Campus.SINGWAN, "홍/은/해", "041301"),
    SINGWAN_VISION(Campus.SINGWAN, "비전/블룸", "041302"),
    SINGWAN_DREAM(Campus.SINGWAN, "드림", "041303"),

    CHEONAN_DORMITORY(Campus.CHEONAN, "생활관식당", "041304");

    private final Campus campus;
    private final String name;
    private final String id;

    DormitoryCafeteria(Campus campus, String name, String id) {
        this.campus = campus;
        this.name = name;
        this.id = id;
    }
}
