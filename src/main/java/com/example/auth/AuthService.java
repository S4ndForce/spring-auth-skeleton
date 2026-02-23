package com.example.auth;

import com.example.exceptions.ConflictException;
import com.example.revoked.RevokedToken;
import com.example.revoked.RevokedTokenRepository;
import com.example.security.JwtUtil;
import com.example.user.Role;
import com.example.user.User;
import com.example.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class AuthService {
    private static final Logger log = LoggerFactory.getLogger(AuthService.class);

    // Part that finalizes the creation of the user entity, NOT user service
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RevokedTokenRepository revokedTokenRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder, RevokedTokenRepository revokedTokenRepository, AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.revokedTokenRepository = revokedTokenRepository;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }


    public void register(RegisterRequest request) {
        String hashedPassword = passwordEncoder.encode(request.password());

        User user = new User(
                request.email(),
                hashedPassword,
                Role.USER
        );

        // prevents race conditions
        try {
            userRepository.save(user);
        } catch (DataIntegrityViolationException ex) {
            throw new ConflictException("Email already registered");
        }
        log.info("User registered: email={}", request.email());
    }

    public void logout(String jti) {
        revokedTokenRepository.save(
                new RevokedToken(jti, Instant.now())
        );
        log.info("Logout successful: jti={}", jti);
    }

    public String login(LoginRequest request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(), request.password()
                )
        );
        log.info("Login successful: email={}", request.email());
        return jwtUtil.generateToken(request.email());
    }

}
