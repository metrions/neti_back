package com.example.neti_back.service;

import com.example.neti_back.dto.QueryRequestDto;
import com.example.neti_back.dto.QueueResponseDto;
import com.example.neti_back.repository.QueueSubjectRepository;
import com.example.neti_back.repository.SessionSubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class QueueService {

    private final SessionSubjectRepository sessionSubjectRepository;

    private final QueueSubjectRepository queueSubjectRepository;

    @Transactional
    public QueueResponseDto choosePlace(QueryRequestDto dto, UUID queueId, String ip) {
        var session = sessionSubjectRepository.findById(queueId).orElseThrow(
                () -> new IllegalStateException("No session found for sessionId: " + queueId)
        );

        if (session.getQueueSubject().getOpenPlaces().containsKey(dto.getPlaceNumber())){
            throw new IllegalStateException("Place number " + dto.getPlaceNumber() + " is already in use");
        }

        if (session.getQueueSubject().getOpenPlaces().containsValue(ip)){
            throw new IllegalStateException("Place number " + dto.getPlaceNumber() + " is already in use");
        }

        var newQueue = new HashMap<Integer, String>(session.getQueueSubject().getOpenPlaces());
        newQueue.put(dto.getPlaceNumber(), ip);
        session.getQueueSubject().setOpenPlaces(newQueue);
        queueSubjectRepository.save(session.getQueueSubject());
        sessionSubjectRepository.save(session);

        var session2 = sessionSubjectRepository.findById(queueId).orElseThrow(
                () -> new IllegalStateException("No session found for sessionId: " + queueId)
        );

        QueueResponseDto queueResponseDto = new QueueResponseDto();
        Set s = session2.getQueueSubject().getOpenPlaces().keySet();
        queueResponseDto.setPlaces(
                List.of(1, 2, 3, 4, 5, 6, 7, 8, 9).stream().filter(x -> !s.contains(x)).toList()
        );

        return queueResponseDto;
    }






}
