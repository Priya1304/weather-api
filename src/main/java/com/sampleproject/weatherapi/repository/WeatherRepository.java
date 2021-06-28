package com.sampleproject.weatherapi.repository;


import com.sampleproject.weatherapi.model.entity.WeatherDataEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface WeatherRepository extends JpaRepository<WeatherDataEntity, Integer> {

    List<WeatherDataEntity> findByNameOrderByDtDesc(@Param("name")String name);



}
