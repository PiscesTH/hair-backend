package com.th.hair.chat;


import com.th.hair.chat.model.ChatDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat")
public class ChatController {
    private final SimpMessagingTemplate messagingTemplate;

    @PostMapping("/sendMessage")
    public ResponseEntity<Void> sendMessage(@RequestBody ChatDto dto) {
        messagingTemplate.convertAndSend("/topic/public", dto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/addUser")
    public ResponseEntity<Void> addUser(@RequestBody ChatDto dto) {
        messagingTemplate.convertAndSend("/topic/public", dto);
        return ResponseEntity.ok().build();
    }
}

