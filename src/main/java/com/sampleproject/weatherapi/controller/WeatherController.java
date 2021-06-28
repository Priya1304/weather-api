package com.sampleproject.weatherapi.controller;

import com.sampleproject.weatherapi.service.ValidationService;
import com.sampleproject.weatherapi.service.WeatherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static java.util.Objects.isNull;

@RestController
@RequestMapping("/v1")
@Slf4j
public class WeatherController {

    private WeatherService weatherService;

    private ValidationService validationService;

    public WeatherController(final WeatherService weatherService,
                             final ValidationService validationService) {
        this.weatherService = weatherService;
        this.validationService = validationService;
    }

    /**
     * Gets the ResponseEntity with weather data
     * @param city
     * @param country
     * @param apiKey
     * @return ResponseEntity with weather data
     * @throws Exception
     */
    @GetMapping(path = "/weather", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getCurrentWeather(@RequestParam(name = "city", required = false) final String city,
                                               @RequestParam(name = "country", required = false) final String country,
                                               @RequestParam(name = "appid", required = false) final String apiKey)
                                                throws Exception {

            log.info("Received GET request, city={} , country= {}", city, country);
            ResponseEntity<?> validationResponse = validationService.validateRequest(city,country,apiKey);

            if (isNull(validationResponse)) {
                ResponseEntity<?> response = weatherService.getCurrentWeather(city, country, apiKey);
                log.info("Sending response: {} ", response);
                return response;

            }
            log.info("Sending response: {} ", validationResponse);
            return  validationResponse;

        }




}
