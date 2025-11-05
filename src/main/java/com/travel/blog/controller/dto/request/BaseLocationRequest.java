package com.travel.blog.controller.dto.request;

import java.time.LocalDateTime;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BaseLocationRequest {
    private String name;
    private String description;
    private Double latitude;
    private Double longitude;
    private String address;
    private LocalDateTime visitedAt;
    private Set<BaseTagRequest> tagRequests;
    private Set<BaseImageRequest> imageRequests;
    private Set<Long> imgListRemove;
}
