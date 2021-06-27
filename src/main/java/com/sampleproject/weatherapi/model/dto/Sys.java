package com.sampleproject.weatherapi.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class Sys {

    private Integer type;

    private Integer id;

    private String country;

    private Long sunrise;

    private Long sunset;

}
