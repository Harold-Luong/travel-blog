package com.travel.blog.service;

import com.travel.blog.controller.dto.request.RatingBaseRequest;
import com.travel.blog.entity.Location;
import com.travel.blog.entity.Rating;
import com.travel.blog.entity.User;
import com.travel.blog.repository.LocationRepository;
import com.travel.blog.repository.RatingRepository;
import com.travel.blog.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LocationRepository locationRepository;

    public Rating createRate(Long userId, RatingBaseRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Location location = locationRepository.findById(request.getLocationId())
                .orElseThrow(() -> new EntityNotFoundException("Location not found"));
        Rating r = Rating.builder()
                .score(request.getScore())
                .comment(request.getComment())
                .user(user)
                .location(location)
                .build();

        return ratingRepository.save(r);
    }
}
