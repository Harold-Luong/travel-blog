package com.travel.blog.controller.location;

import com.travel.blog.mapper.LocationMapper;
import com.travel.blog.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseLocationController {

    @Autowired
    protected LocationService locationService;

    @Autowired
    protected LocationMapper locationMapper;

}
