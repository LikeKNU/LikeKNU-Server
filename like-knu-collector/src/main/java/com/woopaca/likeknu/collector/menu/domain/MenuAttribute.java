package com.woopaca.likeknu.collector.menu.domain;

import io.micrometer.common.util.StringUtils;
import org.jsoup.nodes.Element;

import java.time.LocalDate;

public record MenuAttribute(Element menu, Element date) {

    public static MenuAttribute of(Element menuElement, Element dateElement) {
        return new MenuAttribute(menuElement, dateElement);
    }

    public String getDate() {
        String date = this.date.text();
        if (date.contains("월") && date.contains("일")) {
            int currentYear = LocalDate.now()
                    .getYear();
            return String.join("년 ", String.valueOf(currentYear), date);
        }

        return this.date.text()
                .split(" ")[0];
    }

    public String getMenu() {
        String menu = this.menu.text();
        if (menu.equals("등록된 식단내용이(가) 없습니다.") || StringUtils.isBlank(menu)) {
            return "";
        }

        return menu;
    }
}
