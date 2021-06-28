package com.sampleproject.weatherapi.service;

import com.sampleproject.weatherapi.error.WeatherErrorResponse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.List;

import static com.sampleproject.weatherapi.common.Constants.*;
import static java.util.Objects.isNull;

@Service
@Slf4j
public class ValidationService {

    private static final String REGEX_VALID_CITY_NAME = "[a-zA-Z]+(?:[ '-][a-zA-Z]+)*";



    @Value("${api.apiKeys}")
    private List<String> apiKeys;

    /**
     * Validates the given input
     * @param city
     * @param country
     * @param apiKey
     * @return ResponseEntity with error code and message if validation fails else return null
     */
    public ResponseEntity<?> validateRequest(final String city,
                                             final String country,
                                             final String apiKey) {
        log.info("Received GET request, city={} , country= {} , apiKey= {}", city, country, apiKey);

        if (isNull(city)) {
            return new ResponseEntity<>(WeatherErrorResponse.builder()
                    .cod(MISSING_REQUIRED_ATTRIBUTE_ERROR_CODE)
                    .message(String.format(MISSING_REQUIRED_ATTRIBUTE_ERROR_MESSAGE, "city"))
                    .build(), HttpStatus.BAD_REQUEST);
        } else if (!isValidCityName(city)) {
            return new ResponseEntity<>(WeatherErrorResponse.builder()
                    .cod(INVALID_ATTRIBUTE_VALUE_ERROR_CODE)
                    .message(String.format(INVALID_ATTRIBUTE_VALUE_MESSAGE, "city", "city"))
                    .build(), HttpStatus.NOT_FOUND);
        }


        if (isNull(country)) {
            return new ResponseEntity<>(WeatherErrorResponse.builder()
                    .cod(MISSING_REQUIRED_ATTRIBUTE_ERROR_CODE)
                    .message(String.format(MISSING_REQUIRED_ATTRIBUTE_ERROR_MESSAGE, "country"))
                    .build(), HttpStatus.BAD_REQUEST);
        }

        if (isNull(apiKey)) {
            return new ResponseEntity<>(WeatherErrorResponse.builder()
                    .cod(MISSING_REQUIRED_ATTRIBUTE_ERROR_CODE)
                    .message(String.format(MISSING_REQUIRED_ATTRIBUTE_ERROR_MESSAGE, "appid"))
                    .build(), HttpStatus.BAD_REQUEST);
        } else if (!isValidApiKey(apiKey)) {
            return new ResponseEntity<>(WeatherErrorResponse.builder()
                    .cod(UNAUTHORIZED_API_KEY_ERROR_CODE)
                    .message(String.format(UNAUTHORIZED_API_KEY_MESSAGE, "appid"))
                    .build(), HttpStatus.UNAUTHORIZED);

        }
//        else if (!isApiKeyLimitExceeded(apiKey)) {
//            return new ResponseEntity<>(WeatherErrorResponse.builder()
//                    .cod(UNAUTHORIZED_API_KEY_ERROR_CODE)
//                    .message(String.format(UNAUTHORIZED_API_KEY_MESSAGE, "appid"))
//                    .build(), HttpStatus.TOO_MANY_REQUESTS);
//        }
        return null;

    }

    /**
     * This method does rate limiting on overall request.
     * To be extended to do rate limiting per api key
     * @param apiKey
     * @return
     */
//    private boolean isApiKeyLimitExceeded(String apiKey) {
//      return rateLimiter.tryAcquire();
//
//    }

    /**
     * Checks if api key is valid or not.
     * @param apiKey
     * @return true if key is present in application.yaml file
     */
    private Boolean isValidApiKey(String apiKey) {

        return apiKeys.stream().anyMatch(k -> k.equals(apiKey));
    }


    /**
     *This method uses REGEX to check if city name is valid or not
     * @param city
     * @return true if valid city name else false
     */
    private Boolean isValidCityName(final String city) {
        return city.matches(REGEX_VALID_CITY_NAME);
    }


}
