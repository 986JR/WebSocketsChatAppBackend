package com.message.chat.repository;
// MessageRepository.java
import com.message.chat.model.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findTop50ByRoomOrderBySentAtAsc(String room);
}
