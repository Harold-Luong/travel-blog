package com.travel.blog.controller.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
public class BaseTripResponse {

    protected Long id;
    protected String title;
    protected String description;
    protected String thumbnail;
    protected String status;
    protected String slug;
    @JsonFormat(pattern = "yyyy-MM-dd")
    protected LocalDate startDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    protected LocalDate endDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    protected LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    protected LocalDateTime updatedAt;
}
