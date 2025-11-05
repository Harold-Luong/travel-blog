package com.travel.blog.controller.auth;

import com.travel.blog.entity.User;
import com.travel.blog.repository.UserRepository;
import com.travel.blog.security.jwt.CustomUserDetails;
import com.travel.blog.service.AuthService;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody AuthRequest req) {
        if (userRepository.existsByEmail(req.email())) {
            logger.info("Email is already in use: {}", req.email());
            return ResponseEntity.badRequest().body("Email is already in use");
        }
        User user = new User();
        user.setEmail(req.email());
        user.setPasswordHash(passwordEncoder.encode(req.password()));
        userRepository.save(user);
        logger.info("User registered email: {}", user.getEmail());
        return ResponseEntity.ok("User registered!");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthRequest req) {
        logger.info("req = {}", req);
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.email(), req.password()));

        logger.info("auth = {}", auth);

        CustomUserDetails principal = (CustomUserDetails) auth.getPrincipal();

        logger.info("Login with email successfully: {}", principal.getEmail());
        return ResponseEntity.ok(authService.login(principal));
    }

    @PostMapping("/google")
    public ResponseEntity<?> loginWithGoogle(@RequestBody Map<String, String> body) {
        String idToken = body.get("idToken");
        AuthResponse response = authService.verifyAndLoginGoogle(idToken);
        logger.info("Login with Google successful!");
        return ResponseEntity.ok(response);
    }
}
