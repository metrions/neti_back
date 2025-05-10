package com.example.neti_back.entity.enums;

import java.util.HashMap;

public enum Day {
    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY,
    SATURDAY,
    SUNDAY;

    static HashMap<String, Day> dayHashMap = new HashMap<>(){{
        put("пн", MONDAY);
        put("вт", TUESDAY);
        put("ср", WEDNESDAY);
        put("чт", THURSDAY);
        put("пт", FRIDAY);
        put("сб", SATURDAY);
        put("вс", SUNDAY);
        put("понедельник", MONDAY);
        put("вторник", TUESDAY);
        put("среда", WEDNESDAY);
        put("четверг", THURSDAY);
        put("пятница", FRIDAY);
        put("суббота", SATURDAY);
        put("воскресенье", SUNDAY);
    }};

    public static Day getDay(String day) {
        return dayHashMap.get(day);
    }
}
