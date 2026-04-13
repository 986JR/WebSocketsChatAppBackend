package com.message.chat.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sender;       // username of the sender
    private String content;      // the message text
    private String room;         // chat room name (e.g. "general")
    private LocalDateTime sentAt;

    // MessageType lets clients know what kind of event this is
    public enum MessageType { CHAT, JOIN, LEAVE }
    private MessageType type;
}
