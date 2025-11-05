package com.travel.blog.service;

import com.travel.blog.controller.dto.request.BaseImageRequest;
import com.travel.blog.controller.dto.request.BaseLocationRequest;
import com.travel.blog.controller.dto.request.BaseTagRequest;
import com.travel.blog.entity.Image;
import com.travel.blog.entity.Location;
import com.travel.blog.entity.Tag;
import com.travel.blog.entity.Trip;
import com.travel.blog.repository.LocationRepository;
import com.travel.blog.repository.TripRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Service
public class LocationService {

    @Autowired
    public LocationRepository locationRepository;
    @Autowired
    public TripRepository tripRepository;

    @Autowired
    public TagService tagService;

    public Location getLocationsById(Long id) {
        return locationRepository.findByIdAndIsDeletedFalse(id).orElseThrow(() -> new EntityNotFoundException("Location not found"));
    }

    public Page<Location> getAllLocationByUserId(Long userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("visitedAt").descending());
        return locationRepository.findByTripUserId(userId, pageable);
    }

    public List<Location> getAllLocationByTagSlug(String slug) {
        return locationRepository.findAllByTags_SlugAndTags_IsDeletedFalse(slug);
    }

    @Transactional
    public Location createLocation(Long tripId, BaseLocationRequest request) {

        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new RuntimeException("Trip not found"));

        Location location = Location.builder()
                .trip(trip)
                .name(request.getName())
                .description(request.getDescription())
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .address(request.getAddress())
                .visitedAt(request.getVisitedAt())
                .build();

        Set<Tag> tags = new HashSet<>();
        if (!CollectionUtils.isEmpty(request.getTagRequests())) {
            tags.addAll(tagService.createTags(request.getTagRequests()));
        }

        Set<Image> images = new HashSet<>();
        if (!CollectionUtils.isEmpty(request.getImageRequests())) {
            images.addAll(request.getImageRequests().stream().map(o -> Image.builder()
                    .imageUrl(o.getImageUrl())
                    .description(o.getDescription())
                    .location(location)
                    .build()).collect(Collectors.toSet()));
        }
        location.setTags(tags);
        location.setImages(images);

        return locationRepository.save(location);
    }

    @Transactional
    public Location updateLocation(Long locationId, BaseLocationRequest request) {
        Location location = locationRepository.findByIdAndIsDeletedFalse(locationId)
                .orElseThrow(() -> new EntityNotFoundException("Location not found."));
        
        location.setName(request.getName());
        location.setDescription(request.getDescription());
        location.setAddress(request.getAddress());
        location.setLatitude(request.getLatitude());
        location.setLongitude(request.getLongitude());
        location.setTags(this.newTagUpdate(request.getTagRequests(), location.getTags()));

        if (!CollectionUtils.isEmpty(request.getImgListRemove())) {
            location.getImages().removeIf(img -> request.getImgListRemove().contains(img.getId()));
        }

        if (!CollectionUtils.isEmpty(request.getImageRequests())) {
            for (BaseImageRequest r : request.getImageRequests()) {
                Image img = Image.builder()
                        .imageUrl(r.getImageUrl())
                        .description(r.getDescription())
                        .location(location)
                        .build();
                location.getImages().add(img);
            }
        }
        return locationRepository.save(location);
    }

    public void softDeleteLocation(Long id) {
        Location location = locationRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new EntityNotFoundException("Location not found"));
        location.setIsDeleted(true);
        locationRepository.save(location);
    }

    private Set<Tag> newTagUpdate(Set<BaseTagRequest> tagRequests, Set<Tag> currentTags) {
        // 1. Chuẩn hoá danh sách tag request (lowercase + trim)
        Set<String> newTagNames = tagRequests.stream()
                .map(BaseTagRequest::getName)
                .map(String::trim)
                .map(String::toLowerCase)
                .collect(Collectors.toSet());

        Set<Tag> keepTags = currentTags.stream()
                .filter(tag -> newTagNames.contains(tag.getName().trim().toLowerCase()))
                .collect(Collectors.toSet());

        Set<String> newNamesToAdd = newTagNames.stream()
                .filter(name -> currentTags.stream()
                        .noneMatch(t -> t.getName().equalsIgnoreCase(name)))
                .collect(Collectors.toSet());

        Set<BaseTagRequest> tagReqsToAdd = newNamesToAdd.stream()
                .map(BaseTagRequest::new)
                .collect(Collectors.toSet());
        Set<Tag> newTagUpdate = tagService.createTags(tagReqsToAdd);

        return Stream.concat(keepTags.stream(), newTagUpdate.stream())
                .collect(Collectors.toSet());
    }
}
