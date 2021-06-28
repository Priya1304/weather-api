package com.sampleproject.weatherapi.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WeatherData {

    private String weather_description;
}
