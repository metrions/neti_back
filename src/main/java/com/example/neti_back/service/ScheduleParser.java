package com.example.neti_back.service;

import com.example.neti_back.entity.SessionSubject;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleParser {
    public static Integer WEEK = 1;
    private final HtmlParserService htmlParserService;

    public List<SessionSubject> getLessons(String group, String week) {
        try {
            List<SessionSubject> li = htmlParserService.parseHeadings("ПМИ-22");
            System.out.println(li);
            return li;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
