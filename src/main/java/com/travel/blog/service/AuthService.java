package com.travel.blog.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.travel.blog.controller.auth.AuthResponse;
import com.travel.blog.entity.User;
import com.travel.blog.repository.UserRepository;
import com.travel.blog.security.jwt.CustomUserDetails;
import com.travel.blog.security.jwt.JwtUtil;
import java.time.Instant;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Value("${app.jwt.expiration-ms}")
    private long jwtExpirationMs;

    @Value("${google.client-id}")
    private String GOOGLE_CLIENT_ID;

    public AuthResponse verifyAndLoginGoogle(String idTokenString) {
        try {
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(
                    new NetHttpTransport(),
                    GsonFactory.getDefaultInstance())
                    .setAudience(Collections.singletonList(GOOGLE_CLIENT_ID))
                    .build();
            GoogleIdToken idToken = verifier.verify(idTokenString);
            if (idToken == null) {
                logger.error("GoogleIdToken is null");
                throw new RuntimeException();
            }

            GoogleIdToken.Payload payload = idToken.getPayload();

            Instant now = Instant.now();
            Instant expiry = now.plusMillis(jwtExpirationMs);

            User user = userRepository.findByEmail(payload.getEmail())
                    .map(u -> updateFromGooglePayload(u, payload))
                    .orElseGet(() -> mapDataFromGooglePayloadToSave(payload));

            userRepository.save(user);

            String token = jwtUtil.generateToken(user.getEmail(), user.getId());

            return AuthResponse.of(
                    token,
                    jwtExpirationMs,
                    expiry.toEpochMilli()
            );

        } catch (Exception e) {
            logger.error("Login with Google failed", e);
            throw new RuntimeException("Login with Google failed", e);
        }
    }

    public AuthResponse login(CustomUserDetails principal) {
        Instant now = Instant.now();
        Instant expiry = now.plusMillis(jwtExpirationMs);
        String token = jwtUtil.generateToken(principal.getEmail(), principal.getId());
        return AuthResponse.of(token, jwtExpirationMs, expiry.toEpochMilli());
    }

    private User mapDataFromGooglePayloadToSave(GoogleIdToken.Payload payload) {
        return User.builder()
                .googleId(payload.getSubject())
                .email(payload.getEmail())
                .avatarUrl((String) payload.get("picture"))
                .givenName((String) payload.get("given_name"))
                .familyName((String) payload.get("family_name"))
                .locale((String) payload.get("locale"))
                .build();
    }

    /**
     * DB sẽ lư data tuỳ chỉnh theo user (picture, given_name, family_name) cho phép khác với data từ google
     * Khi login, không update dữ liệu từ google
     *
     * @param user
     * @param payload
     * @return
     */
    public User updateFromGooglePayload(User user, GoogleIdToken.Payload payload) {
        if (user.getGoogleId() == null ) {
            user.setGoogleId(payload.getSubject());
        }

        if(user.getFamilyName() == null) {
            user.setFamilyName((String) payload.get("family_name"));
        }

        if(user.getGivenName() == null) {
            user.setGivenName((String) payload.get("given_name"));
        }

        if (user.getAvatarUrl() == null) {
            user.setAvatarUrl((String) payload.get("picture"));
        }
        return user;
    }
}
