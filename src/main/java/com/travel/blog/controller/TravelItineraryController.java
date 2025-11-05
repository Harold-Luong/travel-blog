package com.travel.blog.controller;

import com.travel.blog.facade.TravelFacade;
import com.travel.blog.facade.TravelItineraryRequest;
import com.travel.blog.facade.TravelItineraryResponse;
import com.travel.blog.security.jwt.CustomUserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/travel-itinerary")
public class TravelItineraryController {
    private static final Logger logger = LoggerFactory.getLogger(TravelItineraryController.class);

    @Autowired
    private TravelFacade travelFacade;

    @PostMapping
    public ResponseEntity<?> createTravelItinerary(@RequestBody TravelItineraryRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        travelFacade.createTravelItinerary(userDetails.getId(), request);
        return ResponseEntity.status(HttpStatus.CREATED).body("Created success!");
    }

    @GetMapping("/{tripId}")
    public ResponseEntity<?> getTravelItinerary(@PathVariable Long tripId) {
        logger.info("Get trip id: {}", tripId);
        TravelItineraryResponse travelItineraryResponse = travelFacade.getTravelItinerary(tripId);
        return ResponseEntity.ok(travelItineraryResponse);
    }
}
