package com.sampleproject.weatherapi.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherDataDTO {

    private Coordinates coord;

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
