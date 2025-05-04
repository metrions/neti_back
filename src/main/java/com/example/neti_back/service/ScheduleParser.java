package com.example.neti_back.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleParser {
    public static Integer WEEK = 1;
    private final HtmlParserService htmlParserService;

    public List<String> getLessons(String group, String week) throws IOException, ParseException {
        List<String> li = htmlParserService.parseHeadings("https://www.nstu.ru/studies/schedule/schedule_classes/schedule?group=ПМИ-22");
        System.out.println(li);
        return li;
    }
}
