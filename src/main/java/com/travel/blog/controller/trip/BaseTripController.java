package com.travel.blog.controller.trip;

import com.travel.blog.mapper.TripMapper;
import com.travel.blog.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseTripController {

    @Autowired
    protected TripService tripService;
    @Autowired
    protected TripMapper tripMapper;
}
