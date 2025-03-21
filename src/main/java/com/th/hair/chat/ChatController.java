package com.th.hair.chat;


import com.th.hair.chat.model.ChatDto;
import com.th.hair.chat.model.ChatListVo;
import com.th.hair.chat.model.MessageVo;
import com.th.hair.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat")
public class ChatController {
    private final ChatService service;

    //메세지 보내기
    @PostMapping("/message")
    public ApiResponse<?> sendMessage(@RequestBody ChatDto dto) {
        service.postMessage(dto);
        return new ApiResponse<>(null);
    }

    //메세지 가져오기
    @GetMapping("/message")
    public ApiResponse<List<MessageVo>> getMessages(@RequestParam Long ichat) {
        return new ApiResponse<>(service.getMessages(ichat));
    }

    //채팅방 리스트 불러오기
    @GetMapping("/chatList")
    public ApiResponse<List<ChatListVo>> getChatList() {
        return new ApiResponse<>(service.getChatList());
    }
}

