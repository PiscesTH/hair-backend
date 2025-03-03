package com.th.hair.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"sender", "receiver"}))
public class Chat extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ichat;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "sender")
    private User sender;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "receiver")
    private User receiver;
}
