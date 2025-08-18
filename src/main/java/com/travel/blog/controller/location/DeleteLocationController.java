package com.travel.blog.controller.location;

import com.travel.blog.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/locations")
public class DeleteLocationController {

    @Autowired
    public LocationService locationService;

    @DeleteMapping("/{locationId}")
    public ResponseEntity<?> deleteLocation(@PathVariable Long locationId) {
        locationService.softDeleteLocation(locationId);
        return ResponseEntity.status(HttpStatus.OK).body("Deleted Location success!");
    }
}

