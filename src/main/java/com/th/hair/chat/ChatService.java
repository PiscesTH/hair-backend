package com.th.hair.chat;

import com.th.hair.chat.model.ChatDto;
import com.th.hair.chat.model.ChatListVo;
import com.th.hair.chat.model.MessageVo;
import com.th.hair.entity.Chat;
import com.th.hair.entity.Message;
import com.th.hair.entity.User;
import com.th.hair.exception.CommonErrorCode;
import com.th.hair.exception.RestApiException;
import com.th.hair.repository.ChatRepository;
import com.th.hair.repository.MessageRepository;
import com.th.hair.repository.UserRepository;
import com.th.hair.security.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        User user = userRepository.findById(iuser).get();
        if (iuser == 1) {
            List<Chat> chatList = chatRepository.findAllByReceiver(user);
            List<ChatListVo> result = chatList.stream().map(item -> (
                    ChatListVo.builder()
                            .ichat(item.getIchat())
                            .receiver(item.getSender().getNm())
                            .sender(user.getNm())
                            .build()
            )).toList();
            return result;
        }
        Chat chatList = chatRepository.findBySender(user);
        if (chatList == null) {
            chatList = new Chat();
            chatList.setSender(user);
            chatList.setReceiver(userRepository.findById(1L).get());
            chatRepository.save(chatList);
        }
        List<ChatListVo> result = new ArrayList<>();
        result.add(ChatListVo.builder()
                .ichat(chatList.getIchat())
                .receiver(chatList.getReceiver().getNm())
                .sender(chatList.getSender().getNm())
                .build());
        return result;
    }

    public List<MessageVo> getMessages(Long ichat) {
        Chat chat = chatRepository.findById(ichat).get();
        List<Message> messageList = messageRepository.findAllByChat(chat);
        List<MessageVo> result = messageList.stream().map(item -> new MessageVo(item.getImessage(), item.getUser().getNm(), item.getMessage())).toList();
        return result;
    }

    public void postMessage(ChatDto dto) {
        long iuser = authenticationFacade.getLoginUserPk();
        log.info("iuser : {}", iuser);
        User user = userRepository.getReferenceById(iuser);
        Optional<Chat> chatOpt = chatRepository.findById(dto.getIchat());
        if (chatOpt.isEmpty()) {
            throw new RestApiException(CommonErrorCode.BAD_REQUEST);
        }
        Message message = new Message();
        message.setChat(chatRepository.getReferenceById(dto.getIchat()));
        message.setMessage(dto.getMessage());
        message.setUser(user);
        messageRepository.save(message);
    }
}
