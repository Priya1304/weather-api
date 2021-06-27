package com.sampleproject.weatherapi.exception;

import com.sampleproject.weatherapi.error.WeatherErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.sampleproject.weatherapi.common.Constants.TECHNICAL_EXCEPTION_CODE;
import static com.sampleproject.weatherapi.common.Constants.TECHNICAL_EXCEPTION_MESSAGE;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestControllerAdvice
@Slf4j
public class WebRestControllerAdvice {

    @ExceptionHandler(Exception.class)
    public ResponseEntity technicalException(Exception e) {
        log.error(e.getMessage(),e);
        WeatherErrorResponse error = WeatherErrorResponse.builder()
                .cod(TECHNICAL_EXCEPTION_CODE)
                .message(TECHNICAL_EXCEPTION_MESSAGE)
                .build();


        return new ResponseEntity(error, INTERNAL_SERVER_ERROR);
    }
}
