package com.woopaca.likeknu.collector.bus;

import lombok.Getter;

import static com.woopaca.likeknu.collector.bus.MapType.KAKAO;
import static com.woopaca.likeknu.collector.bus.MapType.NAVER;

@Getter
public enum DepartureStop {

    KONGJU_ENGINEERING_UNIVERSITY("55020184", "공주대공과대학", NAVER),
    DUJEONG_STATION_ENTRANCE("368516", "두정역입구", NAVER),
    CHEONAN_STATION("368238", "천안역", NAVER),
    SINGWAN_TERMINAL("362043", "종합버스터미널(신관초방면)", NAVER),

    SINGWAN_TERMINAL_KAKAO("BS436082", "종합버스터미널(신관초방면)", KAKAO),
    SINGWAN_TERMINAL_OCRYONG_KAKAO("BS436106", "종합버스터미널(옥룡동방면)", KAKAO),
    JUGONG_APARTMENT_KAKAO("BS435938", "주공1차아파트(터미널방면)", KAKAO),
    JUNGDONG_CROSSROADS_KAKAO("BS435990", "중동사거리(옥룡동방면)", KAKAO);

    private final String stopId;
    private final String stopName;
    private final MapType mapType;

    DepartureStop(String stopId, String stopName, MapType mapType) {
        this.stopId = stopId;
        this.stopName = stopName;
        this.mapType = mapType;
    }
}
