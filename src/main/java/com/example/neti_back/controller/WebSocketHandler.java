package com.example.neti_back.controller;

import com.example.neti_back.dto.QueryRequestDto;
import com.example.neti_back.dto.QueueResponseDto;
import com.example.neti_back.service.QueueService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.UUID;
import java.util.concurrent.CopyOnWriteArraySet;

@RequiredArgsConstructor
@Controller
public class WebSocketHandler extends TextWebSocketHandler {

    private static final CopyOnWriteArraySet<WebSocketSession> sessions = new CopyOnWriteArraySet<>();

    private final QueueService queueService;

    @MessageMapping("/queue/{queueId}")
    @SendTo("/topic/queue/{queueId}")
    public QueueResponseDto handleQuery(
            @Payload QueryRequestDto request,
            @DestinationVariable UUID queueId) {

        return queueService.choosePlace(request, queueId, request.getMail());
    }

    @MessageExceptionHandler(IllegalStateException.class)
    @SendToUser("/queue/errors")
    public String handleIllegalStateException(IllegalStateException ex) {
        return "Ошибка обработки запроса: " + ex.getMessage();
    }

}
