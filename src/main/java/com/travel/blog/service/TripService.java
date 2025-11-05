package com.travel.blog.service;

import com.travel.blog.common.SlugUtil;
import com.travel.blog.controller.dto.request.BaseTripRequest;
import com.travel.blog.entity.Trip;
import com.travel.blog.entity.User;
import com.travel.blog.entity.enums.TRIP_STATUS;
import com.travel.blog.mapper.TripMapper;
import com.travel.blog.repository.TripRepository;
import com.travel.blog.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TripService {

    @Autowired
    private TripRepository tripRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TripMapper tripMapper;

    @Autowired
    private LocationService locationService;

    /**
     * @param userId
     * @param page
     * @param size
     * @return Kết quả được sếp theo ngày gần nhất.
     */
    public Page<Trip> getTripsRecentDiaryByUserId(Long userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("startDate").descending());
        return tripRepository.findAllByUserIdAndIsDeletedFalse(userId, pageable);
    }

    public Trip getTripById(Long tripId) {
        return tripRepository.findByIdAndIsDeletedFalse(tripId)
                .orElseThrow(() -> new EntityNotFoundException("Trip not found"));
    }

    @Transactional
    public Trip createTrip(Long userId, BaseTripRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        Trip trip = Trip.builder()
                .user(user)
                .title(request.getTitle())
                .description(request.getDescription())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .tripStatus(TRIP_STATUS.fromValue(request.getStatus()))

                .build();
        String slug = SlugUtil.generateUniqueSlug(trip.getTitle(),
                s -> tripRepository.existsBySlugAndIsDeletedFalse(s));
        trip.setSlug(slug);

        return tripRepository.save(trip);
    }

    @Transactional
    public Trip updateTrip(Long tripId, BaseTripRequest request) {
        Trip tripUpdate = tripRepository.findByIdAndIsDeletedFalse(tripId)
                .orElseThrow(() -> new EntityNotFoundException("Trip not found"));
            tripUpdate.setTitle(request.getTitle());
            tripUpdate.setDescription(request.getDescription());
            tripUpdate.setStartDate(request.getStartDate());
            tripUpdate.setEndDate(request.getEndDate());
            tripUpdate.setTripStatus(TRIP_STATUS.fromValue(request.getStatus()));

        String slug = SlugUtil.generateUniqueSlug(
                tripUpdate.getTitle(),
                s -> tripRepository.existsBySlugAndIsDeletedFalse(s)
        );

        tripUpdate.setSlug(slug);
        return tripRepository.save(tripUpdate);
    }

    @Transactional
    public void softDeleteTrip(Long tripId) {
        Trip trip = tripRepository.findByIdAndIsDeletedFalse(tripId)
                .orElseThrow(() -> new EntityNotFoundException("Trip not found"));
        trip.setIsDeleted(true);
        tripRepository.save(trip);
    }
}
