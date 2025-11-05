package com.travel.blog.facade;

import com.travel.blog.controller.dto.request.BaseLocationRequest;
import com.travel.blog.controller.dto.request.BaseTripRequest;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TravelItineraryRequest {
    private BaseTripRequest tripRequest;
    private List<BaseLocationRequest> locationItineraryRequestList;
}
