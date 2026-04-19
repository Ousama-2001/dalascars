package com.example.dalascars.repository;

import com.example.dalascars.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findBySenderIdOrReceiverId(Long senderId, Long receiverId);
}