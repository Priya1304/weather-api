package com.sampleproject.weatherapi.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherMain {

    private Double temp;

    private Double feels_like;

    private Double temp_min;

    private Double temp_max;

    private Double pressure;

    private Double humidity;
}
