package com.th.hair.chat;

import com.th.hair.chat.model.ChatListVo;
import com.th.hair.entity.Chat;
import com.th.hair.entity.User;
import com.th.hair.repository.ChatRepository;
import com.th.hair.repository.MessageRepository;
import com.th.hair.repository.UserRepository;
import com.th.hair.security.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatRepository chatRepository;
    private final UserRepository userRepository;
    private final MessageRepository messageRepository;
    private final AuthenticationFacade authenticationFacade;

    public List<ChatListVo> getChatList() {
        long iuser = authenticationFacade.getLoginUserPk();
        User user = userRepository.getReferenceById(iuser);
        if (iuser == 1) {
            List<Chat> chatList = chatRepository.findAllByReceiver(user);
            List<ChatListVo> result = chatList.stream().map(item -> (
                    ChatListVo.builder()
                            .ichat(item.getIchat())
                            .receiverName(item.getSender().getNm())
                            .senderPk(item.getReceiver().getIuser())
                            .build()
            )).toList();
            return result;
        }
        Chat chatList = chatRepository.findBySender(user);
        List<ChatListVo> result = new ArrayList<>();
        result.add(ChatListVo.builder()
                .ichat(chatList.getIchat())
                .receiverName(chatList.getReceiver().getNm())
                .senderPk(chatList.getSender().getIuser())
                .build());
        return result;
    }
}
