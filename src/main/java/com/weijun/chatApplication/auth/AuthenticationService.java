package com.weijun.chatApplication.auth;

import com.weijun.chatApplication.Config.JwtService;
import com.weijun.chatApplication.model.Account;
import com.weijun.chatApplication.model.Role;
import com.weijun.chatApplication.repository.AccountRepository;
import com.weijun.chatApplication.repository.BlockRepository;
import com.weijun.chatApplication.service.AccountService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final AccountRepository accountRepository;
    private final BlockRepository blockRepository;
    private final AccountService accountService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        try {
            var user = Account.builder()
                    .accountname(request.getAccountname())
                    .passwords(passwordEncoder.encode(request.getPasswords()))
                    .email(request.getEmail())
                    .address(request.getAddress())
                    .latitude(request.getLatitude())
                    .longitude(request.getLongitude())
                    .introduction((request.getIntroduction()))
                    .role(Role.USER)
                    .build();
            accountService.signUpUser(user);

            user = accountRepository.findById(user.getAccountname())
                    .orElseThrow(() -> new EntityNotFoundException("User not found after save: " ));

            var jwtToken = jwtService.generateToken(user); //notsure about this
            return AuthenticationResponse.builder().token(jwtToken).build();
        } catch (Exception e) {
            throw new RuntimeException("Failed to register user", e);
        }
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getAccountname(),
                        request.getPasswords()
                )
        );
        var user = accountRepository.findByUsername(request.getAccountname())
                .orElseThrow();
        if (user.getBlock() != null) {
            blockRepository.findById(user.getBlock().getBid()) // Assuming you have a bid field in Blocks
                    .ifPresent(user::setBlock);  // Ensure the block is fully loaded
        }

        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .accountname(user.getUsername())
                .email(user.getEmail())
                .address(user.getAddress())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .latitude(user.getLatitude())
                .introduction(user.getIntroduction())
                .longitude(user.getLongitude())
                .block(user.getBlock() != null ? user.getBlock().getBid() : null)
                .build();
    }

}
