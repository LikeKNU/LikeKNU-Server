package com.woopaca.likeknu.collector.menu.domain;

import java.util.stream.Stream;

public interface CafeteriaAttributeProxy {

    String getMealType();

    Stream<MenuAttribute> menuStream();
}
