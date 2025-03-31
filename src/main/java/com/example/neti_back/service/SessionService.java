package com.example.neti_back.service;

import com.example.neti_back.entity.QueueSubject;
import com.example.neti_back.entity.SessionSubject;
import com.example.neti_back.entity.Subject;
import com.example.neti_back.repository.QueueSubjectRepository;
import com.example.neti_back.repository.SessionSubjectRepository;
import com.example.neti_back.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SessionService {
    private final SessionSubjectRepository sessionSubjectRepository;
    private final SubjectRepository subjectRepository;
    private final QueueSubjectRepository queueSubjectRepository;

    public List<SessionSubject> getSessionByGroup(String group) {
        return sessionSubjectRepository.getSessionSubjectByGroupName(group);
    }

    public List<SessionSubject> getAllSession() {
        return sessionSubjectRepository.findAll();
    }

    public SessionSubject createSession(SessionSubject sessionSubject, UUID subjectId) {
        final Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new IllegalArgumentException("Could not find subject with id: " + subjectId));
        sessionSubject.setSessionSubject(subject);
        QueueSubject queueSubject = new QueueSubject();
        queueSubjectRepository.save(queueSubject);
        sessionSubject.setQueueSubject(queueSubject);
        sessionSubjectRepository.save(sessionSubject);
        return sessionSubject;
    }

    public List<Integer> getOpenPlaces(UUID sessionId) {
        final var session = sessionSubjectRepository.findById(sessionId)
                .orElseThrow(() -> new IllegalArgumentException("Could not find session with id: " + sessionId));
        Set s = session.getQueueSubject().getOpenPlaces().keySet();

        return List.of(1, 2, 3, 4, 5, 6, 7, 8, 9).stream().filter(x -> !s.contains(x)).toList();
    }

}
