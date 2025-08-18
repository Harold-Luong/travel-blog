package com.travel.blog.mapper;

import com.travel.blog.controller.dto.response.BaseTagResponse;
import com.travel.blog.entity.Tag;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class TagMapper {

    public BaseTagResponse toResponse(Tag tag) {
        return BaseTagResponse.builder().id(tag.getId()).name(tag.getName()).slug(tag.getSlug()).build();
    }

    public List<BaseTagResponse> toResponse(List<Tag> tagList) {
        return tagList.stream().map(this::toResponse).toList();
    }
}
