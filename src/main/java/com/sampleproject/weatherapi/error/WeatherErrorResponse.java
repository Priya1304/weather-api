package com.sampleproject.weatherapi.error;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WeatherErrorResponse {

    private String cod;

    private String message;
}
