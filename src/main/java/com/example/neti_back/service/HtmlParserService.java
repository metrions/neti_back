package com.example.neti_back.service;

import com.example.neti_back.entity.SessionSubject;
import com.example.neti_back.entity.Subject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static com.example.neti_back.entity.SessionSubject.Day.*;

@Service
public class HtmlParserService {

    public List<String> parseHeadings(String url) throws IOException, ParseException {
        Document doc = Jsoup.connect(url).get();
        Elements timeBlocks = doc.select(".schedule__table-row:has(.schedule__table-time)");

        List<String> results = new ArrayList<>();
        var li = timeBlocks.stream().filter(x -> x.toString().contains("·") && x.toString().contains("data-empty")).toList();

        for (var timeBlock : li) {
            System.out.println(HtmlParserService.parseSchedule(timeBlock.toString()));

//            String time = timeBlock.select(".schedule__table-time").text().trim();
//            Elements items = timeBlock.select(".schedule__table-item");
//            for (var item : items) {
//                String subjectRaw = item.ownText().trim();
//                String room = item.select(".schedule__table-class").text().trim();
//
//                // Например: "08:30-10:00 | Технологии разработки программного обеспечения | 2-516"
//                String line = time + " | " + subjectRaw + " | " + room;
//                results.add(line);
//            }
        }

        return results;
    }

    static HashMap<String, SessionSubject.Day> dayHashMap = new HashMap<>(){{
        put("пн", MONDAY);
        put("вт", TUESDAY);
        put("ср", WEDNESDAY);
        put("чт", THURSDAY);
        put("пт", FRIDAY);
        put("сб", SATURDAY);
        put("вс", SUNDAY);
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
                currentDay = dayCell.text().replaceAll("\\s+", "");
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

                lesson.setDay(dayHashMap.get(currentDay));
                String startTime = time.substring(0, time.indexOf('-'));
                String endTime  = time.substring(time.indexOf('-')+1);

                lesson.setStartTime(formatter.parse(startTime).getTime());
                lesson.setEndTime(formatter.parse(endTime).getTime());

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