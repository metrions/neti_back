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
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class QueueService {

    private final SessionSubjectRepository sessionSubjectRepository;

    private final QueueSubjectRepository queueSubjectRepository;

    @Transactional
    public QueueResponseDto choosePlace(QueryRequestDto dto, UUID queueId) {
        try {
            var session = sessionSubjectRepository.findById(queueId).orElseThrow(
                    () -> new IllegalStateException("No session found for sessionId: " + queueId)
            );

            var places = session.getQueueSubject().getOpenPlaces();

            if (places.containsKey(dto.getPlaceNumber())) {
                throw new IllegalStateException("Place number " + dto.getPlaceNumber() + " is already in use");
            }
            String name = dto.getName();
            if (places.containsValue(name)) {
                throw new IllegalStateException("You already have place " + name);
            }

            var newQueue = new HashMap<Integer, String>(session.getQueueSubject().getOpenPlaces());
            newQueue.put(dto.getPlaceNumber(), name);
            session.getQueueSubject().setOpenPlaces(newQueue);
            queueSubjectRepository.save(session.getQueueSubject());
            sessionSubjectRepository.save(session);

            var session2 = sessionSubjectRepository.findById(queueId).orElseThrow(
                    () -> new IllegalStateException("No session found for sessionId: " + queueId)
            );

            QueueResponseDto queueResponseDto = new QueueResponseDto();
            Map<Integer, String> s = session2.getQueueSubject().getOpenPlaces();
            queueResponseDto.setPlaces(
                    List.of(1, 2, 3, 4, 5, 6, 7, 8, 9).stream().filter(x -> {
                        return !s.containsKey(x);
                    }).toList()
            );
            queueResponseDto.setPlaceStudents(s);
            return queueResponseDto;
        }
        catch (Exception e){
            var session2 = sessionSubjectRepository.findById(queueId).orElseThrow(
                    () -> new IllegalStateException("No session found for sessionId: " + queueId)
            );
            QueueResponseDto queueResponseDto = new QueueResponseDto();
            Map<Integer, String> s = session2.getQueueSubject().getOpenPlaces();
            queueResponseDto.setPlaces(
                    List.of(1, 2, 3, 4, 5, 6, 7, 8, 9).stream().filter(x -> {
                        return !s.containsKey(x);
                    }).toList()
            );
            queueResponseDto.setPlaceStudents(s);
            return queueResponseDto;
        }
    }

}
