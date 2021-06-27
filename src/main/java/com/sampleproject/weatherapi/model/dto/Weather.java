package com.sampleproject.weatherapi.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class Weather {

    private Integer id;

    private String main;

    private String description;

    private String icon;
}
