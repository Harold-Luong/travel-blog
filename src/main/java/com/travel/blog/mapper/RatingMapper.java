package com.travel.blog.mapper;

import com.travel.blog.controller.dto.response.BaseRatingResponse;
import com.travel.blog.entity.Rating;
import org.springframework.stereotype.Component;


@Component
public class RatingMapper {

    public BaseRatingResponse toResponse(Rating r) {
        return BaseRatingResponse.builder().score(r.getScore()).comment(r.getComment()).build();
    }
}
