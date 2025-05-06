package com.example.neti_back.service;

import com.example.neti_back.entity.QueueSubject;
import com.example.neti_back.entity.SessionSubject;
import com.example.neti_back.entity.Subject;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static com.example.neti_back.entity.SessionSubject.Day.*;

@Service
@RequiredArgsConstructor
public class HtmlParserService {
    private final ModelMapper modelMapper;
    private final String url = "https://www.nstu.ru/studies/schedule/schedule_classes/schedule";

    private String[] getWeekAndDay(Document doc) {
        Elements block = doc.select(".schedule__title-desc:has(.schedule__title-content)");
        if (!block.isEmpty()) {
            Element container = block.first();
            String dayText = container.selectFirst(".schedule__title-content").text().split(", ")[1];
            String weekText = container.selectFirst(".schedule__title-label").text().split(" ")[0];
            return new String[]{dayText, weekText};
        }
        return null;
    }


    public List<SessionSubject> parseHeadings(String group){
        Document doc;
        try {
            doc = Jsoup.connect(url + "?group=" + group).get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String[] dayAndWeek = getWeekAndDay(doc);
        SessionSubject.Day day = dayHashMap.get(dayAndWeek[0]);
        Integer week = Integer.parseInt(dayAndWeek[1]);

        Elements timeBlocks = doc.select(".schedule__table-row:has(.schedule__table-time)");

        List<String> results = new ArrayList<>();
        var li = timeBlocks.stream().filter(x -> x.toString().contains("·") && x.toString().contains("data-empty")).toList();
        List<SessionSubject> sessionSubjects = new ArrayList<>();

        try{
            for (var timeBlock : li) {
                var list = HtmlParserService.parseSchedule(timeBlock.toString());
                list.forEach(x -> x.setGroupName(group));
                sessionSubjects.addAll(list);
            }
        }
        catch (Exception e){
            throw new RuntimeException();
        }

        return sessionSubjects;
    }

    public List<SessionSubject> parseHeadings(String group, String day) throws IOException, ParseException {
        Document doc;
        try {
            doc = Jsoup.connect(url + "?group=" + group).get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Elements timeBlocks = doc.select(".schedule__table-row:has(.schedule__table-time)");

        List<String> results = new ArrayList<>();
        var li = timeBlocks.stream().filter(x -> x.toString().contains("·") && x.toString().contains("data-empty")
            && x.toString().contains(day)).toList();
        List<SessionSubject> sessionSubjects = new ArrayList<>();

        try{
            for (var timeBlock : li) {
                var list = HtmlParserService.parseSchedule(timeBlock.toString());
                list.forEach(x -> x.setGroupName(group));
                sessionSubjects.addAll(list);
            }
        }
        catch (Exception e){
            throw new RuntimeException();
        }

        return sessionSubjects;
    }

    static HashMap<String, SessionSubject.Day> dayHashMap = new HashMap<>(){{
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

    static HashMap<SessionSubject.Day, String> dayToStringHashMap = new HashMap<>(){{
        put(MONDAY, "пн");
        put(TUESDAY, "вт");
        put(WEDNESDAY, "ср");
        put(THURSDAY, "чт");
        put(FRIDAY, "пт");
        put(SATURDAY, "сб");
        put(SUNDAY, "вс");
    }};

    static HashMap<String, Subject.TypeSubject> typeHashMap = new HashMap<>(){{
        put("Лекция", Subject.TypeSubject.LECTURE);
        put("Лабораторная", Subject.TypeSubject.LAB);
    }};

    static DateFormat formatter = new SimpleDateFormat("HH:mm");

    public static List<SessionSubject> parseSchedule(String html) throws ParseException {
        Document doc = Jsoup.parse(html);
        List<SessionSubject> lessons = new ArrayList<>();

        Elements dayRows = doc.select(".schedule__table-row");
        String currentDay = null;

        for (Element row : dayRows) {
            Element dayCell = row.selectFirst(".schedule__table-day");
            if (dayCell != null) {
                currentDay = dayCell.text().replaceAll("\\s+", "").substring(0, 2);
                continue;
            }

            Element timeElement = row.selectFirst(".schedule__table-time");
            if (timeElement == null) continue;

            String time = timeElement.text().trim();

            Element itemElement = row.selectFirst(".schedule__table-item");
            if (itemElement != null && !itemElement.text().trim().isEmpty()) {
                String fullText = itemElement.text().trim();

                String[] parts = fullText.split("·");
                if (parts.length < 2) continue;

                String subject = parts[0].trim();
                String[] restParts = parts[1].trim().split("\\s+");

                int typeIndex = -1;
                for (int i = 0; i < restParts.length; i++) {
                    if (restParts[i].equals("Лекция") || restParts[i].equals("Лабораторная")) {
                        typeIndex = i;
                        break;
                    }
                }
                if (typeIndex == -1) continue;

                String teacher = String.join(" ", Arrays.copyOfRange(restParts, 0, typeIndex));
                String type = restParts[typeIndex];
                String room = typeIndex + 1 < restParts.length ? String.join(" ", Arrays.copyOfRange(restParts, typeIndex + 1, restParts.length)) : "не указана";

                SessionSubject lesson = new SessionSubject();

                lesson.setQueueSubject(new QueueSubject());
                lesson.setDay(dayHashMap.get(currentDay));

                String startTimeStr = time.substring(0, time.indexOf('-')).trim();
                String endTimeStr = time.substring(time.indexOf('-') + 1).trim();

                LocalTime startLocalTime = LocalTime.parse(startTimeStr, DateTimeFormatter.ofPattern("HH:mm"));
                LocalTime endLocalTime = LocalTime.parse(endTimeStr, DateTimeFormatter.ofPattern("HH:mm"));

                lesson.setStartTime((long) startLocalTime.toSecondOfDay());
                lesson.setEndTime((long) endLocalTime.toSecondOfDay());

                lesson.setWeeks(List.of(1, 2, 3));
                Subject subj = new Subject();
                subj.setName(subject);
                subj.setType(typeHashMap.get(type));

                lesson.setSessionSubject(subj);
                lesson.setRoom(room);

                lessons.add(lesson);
            }
        }

        return lessons;
    }
}