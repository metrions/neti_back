package com.example.neti_back.controller;

import com.example.neti_back.dto.SubjectDto;
import com.example.neti_back.entity.Subject;
import com.example.neti_back.service.SubjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/subject")
@Tag(name = "Предметы", description = "Операции с предметами")
public class SubjectController {

    private final SubjectService subjectService;
    private final ModelMapper modelMapper;

    @Operation(summary = "Получить все предметы", description = "Возвращает список всех доступных предметов")
    @ApiResponse(responseCode = "200", description = "Список успешно получен")
    @GetMapping
    public List<Subject> getSubjects() {
        return subjectService.getSubjects();
    }

    @Operation(summary = "Добавить предмет", description = "Создаёт новый предмет на основе переданных данных")
    @ApiResponse(responseCode = "200", description = "Предмет успешно добавлен")
    @PostMapping
    public Subject addSubject(
            @RequestBody SubjectDto subject) {
        return subjectService.createSubject(modelMapper.map(subject, Subject.class));
    }
}
