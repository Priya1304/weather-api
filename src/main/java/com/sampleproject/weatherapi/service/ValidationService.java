package com.sampleproject.weatherapi.service;

import com.sampleproject.weatherapi.error.WeatherErrorResponse;
import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static com.sampleproject.weatherapi.common.Constants.*;
import static java.util.Objects.isNull;

@Service
@Slf4j
public class ValidationService {

    private static final String REGEX_VALID_CITY_NAME = "[a-zA-Z]+(?:[ '-][a-zA-Z]+)*";

    @Value("${api.apiKeys}")
    private List<String> apiKeys;

    public ResponseEntity<?> validateRequest(final String city,
                                             final String country,
                                             final String apiKey) {
        log.info("Received GET request, city={} , country= {} , apiKey= {}", city, country, apiKey);

        if (isNull(city)) {
            log.info("In if");
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
        } else if (!isValidCountryName(country)) {
            return new ResponseEntity<>(WeatherErrorResponse.builder()
                    .cod(INVALID_ATTRIBUTE_VALUE_ERROR_CODE)
                    .message(String.format(INVALID_ATTRIBUTE_VALUE_MESSAGE, "country", "country"))
                    .build(), HttpStatus.NOT_FOUND);
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
        return null;

    }

    private Boolean isValidApiKey(String apiKey) {
        for (String k : apiKeys) {
            log.info("list: {} ", k);
            log.info("value {} ", apiKeys.contains(apiKey));
        }
        return apiKeys.stream().anyMatch(k -> k.equals(apiKey));
    }

    private Boolean isValidCountryName(final String country) {
        Boolean isValidCountryName = false;
        log.info("In country");
        for (String iso : Locale.getISOCountries()) {
            Locale l = new Locale("", iso);
            log.info("In country: iso: {} , dis: {} ",l.getCountry(),  l.getDisplayCountry());
            if (l.getCountry().equalsIgnoreCase(country)
                    || l.getDisplayCountry().equalsIgnoreCase(country)) {
                isValidCountryName = true;
                break;
            }
        }
        return isValidCountryName;

    }

    private Boolean isValidCityName(final String city) {
        return city.matches(REGEX_VALID_CITY_NAME);
    }


}
