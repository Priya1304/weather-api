package com.sampleproject.weatherapi.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Entity
@Table(name = "weather_data")
public class WeatherDataEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String base;

    private String visibility;

    private Long dt;

    private Integer timezone;

    private Integer cod;

    private String name;

    @OneToOne(fetch = FetchType.LAZY, optional = false, cascade= CascadeType.ALL)
    @JoinColumn(name = "coordinates_id", nullable = false)
    private Coordinates coord;

    @OneToMany(fetch = FetchType.LAZY,  cascade= CascadeType.ALL)
    @JoinColumn(name = "weather_id", nullable = false)
    private List<Weather> weather;

    @OneToOne(fetch = FetchType.LAZY, optional = false, cascade= CascadeType.ALL)
    @JoinColumn(name = "main_id", nullable = false)
    private WeatherMain main;

    @OneToOne(fetch = FetchType.LAZY, optional = false, cascade= CascadeType.ALL)
    @JoinColumn(name = "wind_id", nullable = false)
    private Wind wind;

    @OneToOne(fetch = FetchType.LAZY, optional = false, cascade= CascadeType.ALL)
    @JoinColumn(name = "sys_id", nullable = false)
    private Sys sys;


}
