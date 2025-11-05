package com.travel.blog.facade;

import com.travel.blog.entity.Trip;
import com.travel.blog.mapper.LocationMapper;
import com.travel.blog.mapper.TripMapper;
import com.travel.blog.service.LocationService;
import com.travel.blog.service.TripService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class TravelFacade {

    private final TripService tripService;
    private final LocationService locationService;
    private final TripMapper tripMapper;
    private final LocationMapper locationMapper;

    @Transactional
    public void createTravelItinerary(Long userId, TravelItineraryRequest travelItineraryRequest) {
        Trip trip = tripService.createTrip(userId, travelItineraryRequest.getTripRequest());
        travelItineraryRequest.getLocationItineraryRequestList().forEach(o -> locationService.createLocation(trip.getId(), o));
    }

    @Transactional
    public void updateTravelItinerary(Long userId, TravelItineraryRequest travelItineraryRequest) {
        Trip trip = tripService.updateTrip(userId, travelItineraryRequest.getTripRequest());
        travelItineraryRequest.getLocationItineraryRequestList().forEach(o -> locationService.updateLocation(trip.getId(), o));
    }

    public TravelItineraryResponse getTravelItinerary(Long tripId) {
        Trip trip = tripService.getTripById(tripId);
        return new TravelItineraryResponse(tripMapper.toResponse(trip), locationMapper.toResponse(trip.getLocations().stream().toList()));
    }
}
