package com.th.hair.chat.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MessageVo {
    private Long imessage;
    private String message;
    private Long iuser;
}
