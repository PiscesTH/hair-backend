package com.th.hair.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(
                columnNames = {"to", "from"}
        )
})
public class Chat extends BaseEntity{
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ichat;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "to")
    private User to;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "from")
    private User from;
}
