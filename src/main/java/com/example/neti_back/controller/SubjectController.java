package com.example.neti_back.controller;

import com.example.neti_back.dto.SubjectDto;
import com.example.neti_back.entity.Subject;
import com.example.neti_back.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/subject")
public class SubjectController {
    private final SubjectService subjectService;
    private final ModelMapper modelMapper;

    @GetMapping
    public List<Subject> getSubjects() {
        return subjectService.getSubjects();
    }

    @PostMapping
    public Subject addSubject(@RequestBody SubjectDto subject) {
        return subjectService.createSubject(modelMapper.map(subject, Subject.class));
    }
}
