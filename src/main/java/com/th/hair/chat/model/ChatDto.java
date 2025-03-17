package com.th.hair.chat.model;

import lombok.Data;

@Data
public class ChatDto {
    private Long ichat;
    private Long receiverPk;
    private String message;
}
