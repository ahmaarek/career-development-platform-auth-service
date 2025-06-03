package org.sumerge.authservice.Service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.sumerge.authservice.Model.DTO.SignupRequest;
import org.sumerge.authservice.Model.DTO.SignupResponse;
import org.sumerge.authservice.Model.UserAccount;
import org.sumerge.authservice.Repository.UserAccountRepository;
import org.sumerge.authservice.Security.JwtUtil;


@Service
public class AuthService {

    private final UserAccountRepository userAccountRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    AuthService(UserAccountRepository userAccountRepository, PasswordEncoder passwordEncoder,JwtUtil jwtUtil) {
        this.userAccountRepository = userAccountRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public SignupResponse signup(SignupRequest request) {
        if (userAccountRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email is already registered.");
        }

        UserAccount user = UserAccount.builder()
                .email(request.getEmail())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .build();

        userAccountRepository.save(user);
        String token = jwtUtil.generateToken(user.getEmail());

        return new SignupResponse(user.getId(),user.getEmail(), token);
    }


}
