package com.weijun.chatApplication.model;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;


@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Getter
@Setter
@Table(name = "Threads")
public class PostInformation {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer threadid;
    private String feedtype;
    private LocalDate time;
    private String receiver;
    private String subject;
    private String body;
    private int blockid;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "accountname", referencedColumnName = "accountname")
    private Account account;
}
