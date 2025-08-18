package com.travel.blog.controller.dto.response;

import com.travel.blog.entity.Trip;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GetTripDetailsResponse extends BaseTripResponse {

    private List<BaseLocationResponse> locations;

    public GetTripDetailsResponse(Trip trip, List<BaseLocationResponse> locations) {
        super();
        this.locations = locations;
        this.setId(trip.getId());
        this.setTitle(trip.getTitle());
        this.setDescription(trip.getDescription());
        this.setThumbnail(trip.getThumbnail());
        this.setSlug(trip.getSlug());
        this.setStatus(trip.getTripStatus().getName());
        this.setStartDate(trip.getStartDate());
        this.setEndDate(trip.getEndDate());
        this.setCreatedAt(trip.getCreatedAt());
        this.setUpdatedAt(trip.getUpdatedAt());
    }
}
