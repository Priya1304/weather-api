package com.sampleproject.weatherapi.service;

import com.sampleproject.weatherapi.model.entity.WeatherDataEntity;
import com.sampleproject.weatherapi.model.response.WeatherData;
import com.sampleproject.weatherapi.repository.CoordinatesRepository;
import com.sampleproject.weatherapi.repository.WeatherRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Service
@Slf4j
public class WeatherService {

    private WeatherRepository weatherRepository;

    private CoordinatesRepository coordinatesRepository;

    private RestTemplate restTemplate;

    private static final String URI_STRING ="api.openweathermap.org/data/2.5/weather";

    public WeatherService(final WeatherRepository weatherRepository,
                          final  CoordinatesRepository coordinatesRepository,
                          final RestTemplate restTemplate) {
        this.weatherRepository = weatherRepository;
        this.restTemplate = restTemplate;
        this.coordinatesRepository = coordinatesRepository;
    }

    /**
     * This method gets the current weather details by calling Open Weather Map API.
     * The details of API are stored into H2 Database and the required attributes are then queried from H2 database.
     * @param city
     * @param country
     * @param apiKey
     * @return WeatherData response with description
     * @throws Exception
     */
    public ResponseEntity<?> getCurrentWeather(final String city,
                                               final String country,
                                               final String apiKey) throws Exception {
        try {
            log.info("Calling OpenWeatherMap API to get the current weather");
            ResponseEntity<WeatherDataEntity> response = restTemplate
                    .getForEntity(buildOpenWeatherMapURI(city, country, apiKey), WeatherDataEntity.class);
            log.info("Response received from OpenWeatherMap: {}", response);
            if (nonNull(response) && response.hasBody() ) {
                WeatherDataEntity responseBody = response.getBody();
                if (response.getStatusCode().is2xxSuccessful()) {
                    Boolean isSaved =saveWeatherData(responseBody);
                    if (isSaved) {
                        return ResponseEntity.status(response.getStatusCode()).body(fetchAndMapWeatherData(city));
                    }
                }
                return ResponseEntity.status(response.getStatusCode()).body(responseBody);
            }

        } catch(RestClientException ex) {
            log.error("Exception while calling OpenWeatherMap API: {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }

        throw new Exception();
    }


    /**
     * Save the weather data from Open Weather API to H2 database.
     * @param weatherDataEntity
     * @return not null if data is saved
     */
    private Boolean saveWeatherData(WeatherDataEntity weatherDataEntity) {
        return weatherRepository.save(weatherDataEntity) != null;
    }

    /**
     * Queries H2 database to fetch weather data. Map weather data entity to DTO
     * @param name
     * @return Weather data with description
     * @throws Exception
     */
    private WeatherData fetchAndMapWeatherData(final String name) throws Exception {
        log.info("Query name : {} ", name);

        List<WeatherDataEntity> weatherData = weatherRepository.findByNameIgnoreCaseOrderByDtDesc(name);
        log.info("Queried weatherData: {} ", weatherData);
        if (!isNull(weatherData)) {
            return transformEntityToDTO(weatherData);
        }
        throw new Exception();

    }

    /**
     * Forms the URI to call the Open Weather API
     * @param city
     * @param country
     * @param apiKey
     * @return URI
     */
    private URI buildOpenWeatherMapURI(final String city,
                                       final String country,
                                       final String apiKey) {

        URI uri = UriComponentsBuilder
                .newInstance()
                .scheme("https")
                .host(URI_STRING)
                .path("")
                .query("q={city},{country}&appid={appid}")
                .buildAndExpand(city,country,apiKey)
                .toUri();

        log.info("The URI is: {}", uri);
        return  uri;
    }


    /**
     * Transform the Weather Data Entity to DTO
     * @param weatherDataEntity
     * @return
     * @throws Exception
     */
    private WeatherData transformEntityToDTO(final List<WeatherDataEntity> weatherDataEntity) throws Exception {

        try {
            String description = weatherDataEntity.get(0).getWeather().get(0).getDescription();

            return WeatherData.builder()
                    .weather_description(description).build();
        } catch (Exception e) {
            throw new Exception();
        }


    }


}
