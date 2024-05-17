package com.weijun.chatApplication.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountDTO {
    private String accountname;
    private String email;
    private String firstname;
    private String lastname;
    private String introduction;
}
