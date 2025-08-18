package com.travel.blog.controller.rating;

import com.travel.blog.controller.dto.request.RatingBaseRequest;
import com.travel.blog.entity.Rating;
import com.travel.blog.security.jwt.CustomUserDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rating")
public class CreateRatingController extends BaseRatingController {

    @PostMapping
    public ResponseEntity<?> createdRating(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody RatingBaseRequest request) {
        Long userId = customUserDetails.getId();
        Rating r = ratingService.createRate(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ratingMapper.toResponse(r));
    }
}
