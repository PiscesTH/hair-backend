package com.th.hair.repository;

import com.th.hair.entity.Chat;
import com.th.hair.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    @EntityGraph(attributePaths = "sender")
    List<Chat> findAllByReceiver(User user);
    Chat findBySender(User user);
    Chat findByIchatAndSender(Long ichat, User sender);
}
