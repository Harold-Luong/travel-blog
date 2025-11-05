package com.travel.blog.facade;

import com.travel.blog.controller.dto.response.BaseLocationResponse;
import com.travel.blog.controller.dto.response.BaseTripResponse;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TravelItineraryResponse {
    private BaseTripResponse baseTripResponse;
    private List<BaseLocationResponse> baseLocationResponses;
}
