package com.example.neti_back.service;

import com.example.neti_back.entity.enums.Day;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@DependsOn("htmlParserService")
public class TimeService {
    private final HtmlParserService htmlParserService;
    @Getter
    Day day;
    Integer numberWeek;

    public Integer getNumberWeek(){
        return numberWeek;
    }

    @PostConstruct
    public void init(){
        String[] dayAndWeek = htmlParserService.getWeekAndDay();
        numberWeek = Integer.valueOf(dayAndWeek[1]);
        day = Day.getDay(dayAndWeek[0]);
    }
}
