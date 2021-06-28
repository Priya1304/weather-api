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
@Table(name = "sys")
public class Sys {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer sysId;

    private Integer type;

    private String country;

    private Long sunrise;

    private Long sunset;

}
