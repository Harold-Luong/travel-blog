package com.travel.blog.mapper;

import com.travel.blog.controller.dto.response.BaseImageResponse;
import com.travel.blog.controller.dto.response.BaseLocationResponse;
import com.travel.blog.controller.dto.response.BaseTagResponse;
import com.travel.blog.controller.dto.response.BaseTripResponse;
import com.travel.blog.controller.dto.response.GetTripDetailsResponse;
import com.travel.blog.entity.Trip;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class TripMapper {

    public BaseTripResponse toResponse(Trip trip) {
        return BaseTripResponse.builder()
                .id(trip.getId())
                .title(trip.getTitle())
                .slug(trip.getSlug())
                .thumbnail(trip.getThumbnail())
                .description(trip.getDescription())
                .status(trip.getTripStatus().getName())
                .startDate(trip.getStartDate())
                .endDate(trip.getEndDate())
                .createdAt(trip.getCreatedAt())
                .updatedAt(trip.getUpdatedAt())
                .build();
    }

    public BaseTripResponse toResponseTripDetails(Trip trip) {
        List<BaseLocationResponse> locationSummaryList = trip.getLocations().stream()
                .map(location -> BaseLocationResponse.builder()
                        .id(location.getId())
                        .name(location.getName())
                        .latitude(location.getLatitude())
                        .longitude(location.getLongitude())
                        .address(location.getAddress())
                        .visitedAt(location.getVisitedAt())
                        .description(location.getDescription())
//                        .tags(
//                                location.getTags() != null
//                                        ? location.getTags().stream()
//                                        .map(tag -> BaseTagResponse.builder()
//                                                .id(tag.getId())
//                                                .name(tag.getName())
//                                                .slug(tag.getSlug())
//                                                .build())
//                                        .collect(Collectors.toSet())
//                                        : Collections.emptySet()
//                        )
//                        .images(
//                                location.getImages() != null
//                                        ? location.getImages().stream()
//                                        .map(img -> BaseImageResponse.builder()
//                                                .id(img.getId())
//                                                .imageUrl(img.getImageUrl())
//                                                .description(img.getDescription())
//                                                .build())
//                                        .collect(Collectors.toSet())
//                                        : Collections.emptySet()
//                        )
                        .build()
                )
                .toList();
        return new GetTripDetailsResponse(trip, locationSummaryList);
    }
}
