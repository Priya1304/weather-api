package com.sampleproject.weatherapi.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "weather_main")
public class WeatherMain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer mid;

    private Double temp;

    private Double feels_like;

    private Double temp_min;

    private Double temp_max;

    private Double pressure;

    private Double humidity;
}
