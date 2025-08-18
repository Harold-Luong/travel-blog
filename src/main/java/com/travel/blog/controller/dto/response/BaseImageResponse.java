package com.travel.blog.controller.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BaseImageResponse {

    private Long id;
    private String imageUrl;
    private String description;
    private String aiTags;
    private String faceClusterId;
}
