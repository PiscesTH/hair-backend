package com.th.hair.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
public class Message extends CreatedAtEntity{
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imessage;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "ichat")
    private Chat chat;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "iuser")
    private User user;

    @Column
    private String message;

}
