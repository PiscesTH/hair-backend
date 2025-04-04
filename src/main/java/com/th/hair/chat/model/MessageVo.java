package com.th.hair.chat.model;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MessageVo {
    private Long imessage;
    private String sender;
    private String message;
}
