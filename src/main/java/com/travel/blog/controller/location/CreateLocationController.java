package com.travel.blog.controller.location;

import com.travel.blog.controller.dto.request.BaseLocationRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/locations")
public class CreateLocationController extends BaseLocationController {

    @PostMapping("/{tripId}")
    public ResponseEntity<?> createLocation(@PathVariable Long tripId,  @RequestBody BaseLocationRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(locationMapper.toResponse(locationService.createLocation(tripId, request)));
    }
}
