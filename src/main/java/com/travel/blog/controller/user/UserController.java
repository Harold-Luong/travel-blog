package com.travel.blog.controller.user;

import com.travel.blog.entity.User;
import com.travel.blog.security.jwt.CustomUserDetails;
import com.travel.blog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/me")
@RequiredArgsConstructor
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    @GetMapping()
    public ResponseEntity<?> me(Authentication authentication) {
        if (authentication == null) {
            return ResponseEntity.status(401).build();
        }
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        logger.info("Get user mail: {}", userDetails.getEmail());
        return ResponseEntity.ok(new MeResponse(
                userDetails.getEmail(),
                userDetails.getGivenName(),
                userDetails.getFamilyName(),
                userDetails.getAvatar(),
                userDetails.getBio()));
    }

    @PostMapping(value = "/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MeResponse> update(Authentication authentication,
            @RequestPart("data") UserUpdateRequest request,
            @RequestPart(value = "avatar", required = false) MultipartFile avatar) {

        if (authentication == null) {
            return ResponseEntity.status(401).build();
        }
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        logger.info("Update for user mail: {}", userDetails.getEmail());
        logger.info("Data : {}", request);
        User newUser = userService.update(userDetails.getId(), request, avatar);

        return ResponseEntity.status(HttpStatus.OK).body(
                new MeResponse(newUser.getEmail(),
                        newUser.getGivenName(),
                        newUser.getFamilyName(),
                        newUser.getAvatarUrl(),
                        newUser.getBio()
                ));
    }

    public record UserUpdateRequest(String bio, String givenName, String familyName) {

    }

    public record MeResponse(String email, String givenName, String familyName, String avatar, String bio) {

    }
}