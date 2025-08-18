package com.travel.blog.mapper;

import com.travel.blog.controller.dto.response.BaseImageResponse;
import com.travel.blog.controller.dto.response.BaseLocationResponse;
import com.travel.blog.controller.dto.response.BaseRatingResponse;
import com.travel.blog.controller.dto.response.BaseTagResponse;
import com.travel.blog.controller.dto.response.GetLocationDetailsResponse;
import com.travel.blog.entity.Location;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class LocationMapper {

    public BaseLocationResponse toResponse(Location location) {
        return BaseLocationResponse.builder()
                .id(location.getId())
                .name(location.getName())
                .description(location.getDescription())
                .latitude(location.getLatitude())
                .longitude(location.getLongitude())
                .address(location.getAddress())
                .visitedAt(location.getVisitedAt())
                .build();
    }

    public List<BaseLocationResponse> toResponse(List<Location> locations) {
        return locations.stream().map(this::toResponse).collect(Collectors.toList());
    }

    public GetLocationDetailsResponse toResponseDetailsList(Location location) {
        Set<BaseTagResponse> tags = location.getTags() != null
                ? location.getTags().stream()
                .map(o -> BaseTagResponse.builder()
                        .id(o.getId())
                        .name(o.getName())
                        .slug(o.getSlug())
                        .build())
                .collect(Collectors.toSet())
                : Collections.emptySet();

        Set<BaseImageResponse> images = location.getImages() != null
                ? location.getImages().stream()
                .map(img -> BaseImageResponse.builder()
                        .id(img.getId())
                        .imageUrl(img.getImageUrl())
                        .description(img.getDescription())
                        .build())
                .collect(Collectors.toSet())
                : Collections.emptySet();

        List<BaseRatingResponse> ratings = location.getRatings() != null
                ? location.getRatings().stream()
                .map(rate -> BaseRatingResponse.builder()
                        .score(rate.getScore())
                        .comment(rate.getComment()).build()).collect(Collectors.toList())
                : Collections.emptyList();
        return new GetLocationDetailsResponse(location, tags, images, ratings);
    }
}
