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

    static HashMap<String, Day> dayToString = new HashMap<String, Day>(){{
        put("MONDAY", MONDAY);
        put("TUESDAY", TUESDAY);
        put("WEDNESDAY", WEDNESDAY);
        put("THURSDAY", THURSDAY);
        put("FRIDAY", FRIDAY);
        put("SATURDAY", SATURDAY);
        put("SUNDAY", SUNDAY);
    }};

    public static Day getDay(String day) {
        return dayHashMap.get(day);
    }

    @Override
    public String toString() {
        return name();
    }

}
