package com.travel.blog.controller.dto.response;

import com.travel.blog.entity.Location;
import java.util.List;
import java.util.Set;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class GetLocationDetailsResponse extends BaseLocationResponse {
    private Set<BaseTagResponse> tags;
    private Set<BaseImageResponse> images;
    private List<BaseRatingResponse> ratings;

    public GetLocationDetailsResponse(Location location, Set<BaseTagResponse> tagResponses, Set<BaseImageResponse>  imageResponses, List<BaseRatingResponse> ratingResponses) {
        super(location.getId(), location.getName(), location.getDescription(), location.getLatitude(), location.getLongitude(), location.getAddress(), location.getVisitedAt());
        this.images = imageResponses;
        this.ratings = ratingResponses;
        this.tags = tagResponses;
    }
}
