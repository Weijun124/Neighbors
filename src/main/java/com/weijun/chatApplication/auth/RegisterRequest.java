package com.weijun.chatApplication.auth;

import com.weijun.chatApplication.model.Blocks;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String accountname;
    private String passwords;
    private String email;
    private String address;
    private float latitude;
    private float longitude;
    private String firstname;
    private String lastname;
    private String introduction;
}
