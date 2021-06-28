package com.sampleproject.weatherapi.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherDataDTO {

    private CoordinatesDTO coord;

    private List<Weather>  weather;

    private String base;

    private WeatherMain main;

    private String visibility;

    private Wind wind;

    private Clouds clouds;

    private Long dt;

    private Sys sys;

    private Integer id;

    private Integer timezone;

    private Integer cod;

    private String name;

}
