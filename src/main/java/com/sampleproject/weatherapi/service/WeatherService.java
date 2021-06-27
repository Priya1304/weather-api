package com.sampleproject.weatherapi.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.sampleproject.weatherapi.error.WeatherErrorResponse;
import com.sampleproject.weatherapi.model.dto.WeatherDataDTO;
import com.sampleproject.weatherapi.model.entity.Weather;
import com.sampleproject.weatherapi.model.response.WeatherData;
import com.sampleproject.weatherapi.repository.WeatherRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static java.util.Objects.nonNull;

@Service
@Slf4j
public class WeatherService {

    private WeatherRepository weatherRepository;

    private RestTemplate restTemplate;

    private static final String DESCRIPTION_FIELD_PATH = "/weather/0/description";
    private static final String URI_STRING ="api.openweathermap.org/data/2.5/weather";

    public WeatherService(final WeatherRepository weatherRepository,
                          final RestTemplate restTemplate) {
        this.weatherRepository = weatherRepository;
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<?> getCurrentWeather(final String city,
                                               final String country,
                                               final String apiKey) throws Exception {
        try {
            log.info("Calling OpenWeatherMap API to get current weather");
            ResponseEntity<WeatherDataDTO> response = restTemplate
                    .getForEntity(buildOpenWeatherMapURI(city, country, apiKey), JsonNode.class);
            log.info("Response received from OpenWeatherMap: {}", response);
            if (nonNull(response) && response.hasBody() ) {
                JsonNode responseBody = response.getBody();
                if (response.getStatusCode().is2xxSuccessful()) {
                    Boolean isSaved =saveWeatherData(responseBody);
                    if (isSaved) {
                        return ResponseEntity.status(response.getStatusCode()).body(mapWeatherData(responseBody));
                        //return queryAndFetchWeatherData();
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

    private ResponseEntity<?> queryAndFetchWeatherData() {
        return null;
    }

    private Boolean saveWeatherData(JsonNode responseBody) {
        final String weatherDescription = responseBody.isMissingNode() ? null :
                responseBody.at(DESCRIPTION_FIELD_PATH).asText();
        Weather weatherEntity = Weather.builder().description(weatherDescription).build();
        return weatherRepository.save(weatherEntity) != null;
    }

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

        log.info("uri  : {}", uri);
        return  uri;
    }

    private WeatherData mapWeatherData(final JsonNode responseBody) {
        final String weatherDescription = responseBody.isMissingNode() ? null :
                responseBody.at(DESCRIPTION_FIELD_PATH).asText();
        log.info("Response weatherDescription {}", weatherDescription);
        return WeatherData.builder()
                .description(weatherDescription).build();


    }

    private WeatherErrorResponse formErrorResponse(final String code,
                                                   final String message) {
        return WeatherErrorResponse.builder()
                .cod(code)
                .message(message)
                .build();
    }


}
