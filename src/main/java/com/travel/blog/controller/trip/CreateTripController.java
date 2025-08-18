package com.travel.blog.controller.trip;

import com.travel.blog.controller.dto.request.BaseTripRequest;
import com.travel.blog.security.jwt.CustomUserDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/trip")
public class CreateTripController extends BaseTripController {

    @PostMapping
    public ResponseEntity<?> createTrip(@RequestBody BaseTripRequest request, @AuthenticationPrincipal CustomUserDetails userDetails) {
        Long userId = userDetails.getId();
        return ResponseEntity.status(HttpStatus.CREATED).body(tripMapper.toResponse(tripService.createTrip(userId, request)));
    }
}

