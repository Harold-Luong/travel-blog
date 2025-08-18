package com.travel.blog.controller.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class RatingBaseRequest {
    private int score;
    private String comment;
    private Long locationId;
}
