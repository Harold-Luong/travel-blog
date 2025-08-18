package com.travel.blog.controller.trip;

import com.travel.blog.controller.dto.request.BaseTripRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/trip")
public class UpdateTripController extends BaseTripController {

    @PutMapping("/{tripId}")
    public ResponseEntity<?> updateTrip(@PathVariable Long tripId, @RequestBody BaseTripRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(tripMapper.toResponse(tripService.updateTrip(tripId, request)));
    }
}

