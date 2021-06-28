package com.sampleproject.weatherapi.service;

import com.sampleproject.weatherapi.error.WeatherErrorResponse;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;


class ValidationServiceTest {


    ValidationService validationService;

    @BeforeEach
    public void init() {

        validationService = new ValidationService();
        ReflectionTestUtils.setField(validationService, "apiKeys",
                Arrays.asList("521c6bc368f45024e808037bbc5926c5","c12d5729c47e40fac71889805db9488e"
                        ,"a2bb897ca0dce9e9fef10e091f3cfd37","42e7bb01ed11b8139cf006e735e1c502",
                        "5e23765da16164e9b19092c0b59262d2"));
    }

    @Test
    public void shouldReturnMissingRequiredAttributeWhenCityIsMissing() {

        WeatherErrorResponse weatherErrorResponse = getExpectedResponse("400",
                "Missing required field country");
        ResponseEntity<?> actualResponse = validationService.validateRequest("London", null,
                "123");

        assertThat(actualResponse.getStatusCode(), is(HttpStatus.BAD_REQUEST));
        assertThat(actualResponse.getBody(), is(weatherErrorResponse));

    }

    @Test
    public void shouldReturnMissingRequiredAttributeWhenAPIKeyIsMissing() {

        WeatherErrorResponse weatherErrorResponse = getExpectedResponse("400",
                "Missing required field appid");
        ResponseEntity<?> actualResponse = validationService.validateRequest("London", "UK",
                null);

        assertThat(actualResponse.getStatusCode(), is(HttpStatus.BAD_REQUEST));
        assertThat(actualResponse.getBody(), is(weatherErrorResponse));

    }

    @Test
    public void shouldReturnMissingRequiredAttributeWhenAPIKeyIsInvalid() {

        WeatherErrorResponse weatherErrorResponse = getExpectedResponse("401",
                "Invalid API key.");
        ResponseEntity<?> actualResponse = validationService.validateRequest("London", "UK",
                "123");

        assertThat(actualResponse.getStatusCode(), is(HttpStatus.UNAUTHORIZED));
        assertThat(actualResponse.getBody(), is(weatherErrorResponse));

    }

    private WeatherErrorResponse getExpectedResponse(final String code,
                                                     final String message) {
        return WeatherErrorResponse.builder()
                .cod(code)
                .message(message)
                .build();

    }

}