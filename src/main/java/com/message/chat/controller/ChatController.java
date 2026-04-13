package com.message.chat.controller;

import com.message.chat.model.ChatMessage;
import com.message.chat.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@org.springframework.stereotype.Controller
@RequiredArgsConstructor
public class ChatController {

    private final MessageRepository messageRepository;
    private final SimpMessagingTemplate messagingTemplate;  // lets us push messages server-side

    /**
     * Client sends a message to: /app/chat.send/{room}
     * Server broadcasts it to:   /topic/chat/{room}
     * ALL subscribers of that room receive it instantly.
     */
    @MessageMapping("/chat.send/{room}")
    @SendTo("/topic/chat/{room}")               // broadcast to everyone in this room
    public ChatMessage sendMessage(
            @DestinationVariable String room,
            @Payload ChatMessage message) {

        message.setRoom(room);
        message.setSentAt(LocalDateTime.now());
        message.setType(ChatMessage.MessageType.CHAT);
        messageRepository.save(message);
        return message;                         // returned object is broadcast
    }

    /**
     * Client sends JOIN event to: /app/chat.join/{room}
     * Everyone in that room sees the announcement.
     */
    @MessageMapping("/chat.join/{room}")
    @SendTo("/topic/chat/{room}")
    public ChatMessage joinRoom(
            @DestinationVariable String room,
            @Payload ChatMessage message) {

        message.setRoom(room);
        message.setSentAt(LocalDateTime.now());
        message.setType(ChatMessage.MessageType.JOIN);
        message.setContent(message.getSender() + " joined the room");
        return message;
    }

    /**
     * REST endpoint to fetch the last 50 messages for a room
     * (for loading chat history when a user opens the app)
     * GET /api/chat/history/{room}
     */
    @GetMapping("/api/chat/history/{room}")
    @org.springframework.web.bind.annotation.ResponseBody
    public List<ChatMessage> getHistory(@PathVariable String room) {
        return messageRepository.findTop50ByRoomOrderBySentAtAsc(room);
    }
}