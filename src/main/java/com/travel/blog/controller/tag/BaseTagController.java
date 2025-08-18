package com.travel.blog.controller.tag;

import com.travel.blog.mapper.TagMapper;
import com.travel.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseTagController {

    @Autowired
    protected TagService tagService;

    @Autowired
    protected TagMapper tagMapper;
}
