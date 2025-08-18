package com.travel.blog.controller.location;

import com.travel.blog.common.PagedResponse;
import com.travel.blog.entity.Location;
import com.travel.blog.security.jwt.CustomUserDetails;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/locations")
public class GetLocationController extends BaseLocationController {

    @GetMapping("/me")
    public ResponseEntity<?> getAllLocationsByUser (
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size) {
        Long userId = userDetails.getId();
        return ResponseEntity.ok(new PagedResponse<>(locationService.getAllLocationByUserId(userId, page, size).map(locationMapper::toResponse)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getLocationsDetailsById(@PathVariable Long id) {
        return ResponseEntity.ok(locationMapper.toResponseDetailsList(locationService.getLocationsById(id)));
    }

    @GetMapping("/tag/{slug}")
    public ResponseEntity<?> getLocationsByTagSlug(@PathVariable String slug) {
        List<Location> locations = locationService.getAllLocationByTagSlug(slug);
        return ResponseEntity.ok(locationMapper.toResponse(locations));
    }
}
