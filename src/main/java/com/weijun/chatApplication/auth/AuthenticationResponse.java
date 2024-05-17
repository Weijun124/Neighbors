package com.weijun.chatApplication.auth;

import com.weijun.chatApplication.model.Blocks;
import com.weijun.chatApplication.repository.AccountRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
    private String token;
    private String accountname;
    private String email;
    private String firstname;
    private String lastname;
    private String address;
    private float latitude;
    private float longitude;
    private Integer block;
    private String introduction;

}
