package com.travel.blog.controller.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BaseLocationResponse {

    private Long id;
    private String name;
    private String description;
    private Double latitude;
    private Double longitude;
    private String address;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime visitedAt;
}
