package com.th.hair.chat.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChatListVo {
    private Long ichat;
    private String receiver;
    private String sender;
}
