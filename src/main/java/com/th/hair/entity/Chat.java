package com.th.hair.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"to_user", "from_user"}))
public class Chat extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ichat;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "to_user")
    private User to;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "from_user")
    private User from;
}
