package com.example.neti_back.dto;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class SessionDto {
    private UUID id;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime startTime;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime endTime;

    private String day;

    private String subjectName;

    private List<Integer> weeks;

    private String room;

    private String group;

    private UUID queueSubject;
}
