package com.travel.blog.controller.trip;

import com.travel.blog.common.PagedResponse;
import com.travel.blog.entity.Trip;
import com.travel.blog.security.jwt.CustomUserDetails;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/trip")
public class GetTripController extends BaseTripController {

    @GetMapping("/details/{tripId}")
    public ResponseEntity<?> getTripDetailsById(@PathVariable Long tripId) {
        return ResponseEntity.ok(tripMapper.toResponseTripDetails(tripService.getTripById(tripId)));
    }

    @GetMapping("/me")
    public ResponseEntity<PagedResponse<?>> getTripsRecentDiaryByUserId(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size) {
        Page<Trip> trips = tripService.getTripsRecentDiaryByUserId(userDetails.getId(), page, size);
        return ResponseEntity.ok(new PagedResponse<>(trips.map(tripMapper::toResponse)));
    }
}
