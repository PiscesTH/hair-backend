package com.th.hair.chat;


import com.th.hair.chat.model.ChatDto;
import com.th.hair.chat.model.ChatListVo;
import com.th.hair.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat")
public class ChatController {
    private final SimpMessagingTemplate messagingTemplate;
    private final ChatService service;

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

    @GetMapping("/list")
    public ApiResponse<List<ChatListVo>> getChatList() {
        return new ApiResponse<>(service.getChatList());
    }
}

