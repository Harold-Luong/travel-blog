package com.travel.blog.controller.location;

import com.travel.blog.controller.dto.request.BaseLocationRequest;
import com.travel.blog.controller.dto.response.BaseLocationResponse;
import com.travel.blog.entity.Location;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/locations")
public class UpdateLocationController extends BaseLocationController {

    @PutMapping("/{id}")
    public ResponseEntity<BaseLocationResponse> updateLocation(@PathVariable Long id, @RequestBody BaseLocationRequest request) {
        Location location = locationService.updateLocation(id, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(locationMapper.toResponse(location));
    }
}
