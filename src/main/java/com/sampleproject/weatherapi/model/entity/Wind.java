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
@Table(name = "wind")
public class Wind {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer windId;

    private Double speed;

    private Double deg;
}
