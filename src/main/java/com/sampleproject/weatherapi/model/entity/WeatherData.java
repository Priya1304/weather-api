package com.sampleproject.weatherapi.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Entity
public class WeatherData{

    @Id
    private Integer id;

    private String base;

    private String visibility;

    private Date dt;

    private Integer timezone;

    private Integer cod;

    private String name;

}
