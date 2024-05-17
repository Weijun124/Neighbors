package com.weijun.chatApplication.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ThreadDTO {
    private Integer threadId;
    private String feedType;
    private String receiver;
    private LocalDate time;
    private String subject;
    private String body;
    private int blockId;
    private String accountname;
}
