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
@Table(name = "weather")
public class Weather {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer wid;

    private Integer id;

    private String main;

    private String description;

    private String icon;

//    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private WeatherDataEntity weatherDataEntity;
}
