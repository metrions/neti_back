package com.example.neti_back.controller;

import com.example.neti_back.dto.QueueResponseDto;
import com.example.neti_back.dto.SessionDto;
import com.example.neti_back.entity.SessionSubject;
import com.example.neti_back.entity.enums.Day;
import com.example.neti_back.service.SessionService;
import com.example.neti_back.service.TimeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/session")
@Tag(name = "Сессии", description = "Управление сессиями и расписанием")
public class SessionController {

    private final SessionService sessionService;
    private final ModelMapper modelMapper;
    private final TimeService timeService;
    private final String defaultGroup = "ПМИ-22";

    @Operation(summary = "Добавить лабораторную", description = "Создаёт новую сессию для указанного предмета")
    @ApiResponse(responseCode = "200", description = "Сессия успешно добавлена")
    @PostMapping
    public SessionSubject addSessionSubject(
            @RequestBody SessionDto sessionSubject,
            @Parameter(description = "ID предмета", required = true)
            @RequestParam UUID subjectId) {
        return sessionService.createSession(modelMapper.map(sessionSubject, SessionSubject.class), subjectId);
    }

    @Operation(summary = "Получить список сессий", description = "Возвращает список всех сессий по дню, группе и неделе")
    @GetMapping
    public List<SessionDto> getAllSessionSubject(
            @Parameter(description = "День недели (пример: пн, вт, ср)", example = "пн")
            @RequestParam(required = false) String day,
            @Parameter(description = "Название группы", example = "ПМИ-22")
            @RequestParam(required = false) String group,
            @Parameter(description = "Номер учебной недели", example = "13")
            @RequestParam(required = false) Integer weekNumber) {

        Day dayEnum = day == null ? timeService.getDay() : Day.getDay(day);
        Integer week = weekNumber == null ? timeService.getNumberWeek() : weekNumber;
        String groupEn = group == null ? defaultGroup : group;

        return sessionService.getAllSession(dayEnum, groupEn).stream()
                .filter(x -> x.getWeek().equals(week))
                .map(x -> modelMapper.map(x, SessionDto.class))
                .toList();
    }

    @Operation(summary = "Получить свободные места", description = "Возвращает список открытых мест по ID сессии")
    @GetMapping("/{id}")
    public QueueResponseDto getOpenPlaces(
            @Parameter(description = "ID сессии", required = true)
            @PathVariable("id") UUID sessionId) {
        return sessionService.getOpenPlaces(sessionId);
    }

    @Operation(summary = "Инициализировать сессии из расписания", description = "Парсит расписание и инициализирует все сессии")
    @PostMapping("/init")
    public void getAllSession() {
        sessionService.initFromSchedule();
    }

    @Operation(summary = "Удалить все сессии", description = "Очищает все сессии из базы данных")
    @DeleteMapping("/all")
    public void deleteAllSession() {
        sessionService.clear();
    }
}
