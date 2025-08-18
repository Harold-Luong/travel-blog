package com.travel.blog.controller.rating;

import com.travel.blog.mapper.RatingMapper;
import com.travel.blog.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseRatingController {
    @Autowired
    protected RatingService ratingService;

    @Autowired
    protected RatingMapper ratingMapper;



}
