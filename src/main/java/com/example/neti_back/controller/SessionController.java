package com.example.neti_back.controller;

import com.example.neti_back.dto.SessionDto;
import com.example.neti_back.entity.SessionSubject;
import com.example.neti_back.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/session")
public class SessionController {
    private final SessionService sessionService;
    private final ModelMapper modelMapper;

    @PostMapping
    public SessionSubject addSessionSubject(@RequestBody SessionDto sessionSubject,
                                            @RequestParam UUID subjectId) {
        return sessionService.createSession(modelMapper.map(sessionSubject, SessionSubject.class), subjectId);
    }

    @GetMapping
    public List<SessionDto> getAllSessionSubject() {
        return sessionService.getAllSession().stream().map(
                x -> modelMapper.map(x, SessionDto.class)
        ).toList();
    }

//    @GetMapping("/{group}")
//    public List<SessionDto> getAllSessionSubject(@PathVariable("group") String group) {
//        return sessionService.getSessionByGroup(group).stream().map(
//                x -> modelMapper.map(x, SessionDto.class)
//        ).toList();
//    }

    @GetMapping("/{id}")
    public List<Integer> getOpenPlaces(@PathVariable("id") UUID subjectId) {
        return sessionService.getOpenPlaces(subjectId);
    }

}
