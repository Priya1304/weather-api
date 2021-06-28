package com.sampleproject.weatherapi.common;

public final class Constants {

    public static final String TECHNICAL_EXCEPTION_MESSAGE = "Internal error";
    public static final String TECHNICAL_EXCEPTION_CODE = "500";

    public static final String MISSING_REQUIRED_ATTRIBUTE_ERROR_MESSAGE = "Missing required field %s";
    public static final String MISSING_REQUIRED_ATTRIBUTE_ERROR_CODE= "400";

    public static final String INVALID_ATTRIBUTE_VALUE_MESSAGE = "Invalid %s name, %s not found";
    public static final String INVALID_ATTRIBUTE_VALUE_ERROR_CODE= "404";

    public static final String UNAUTHORIZED_API_KEY_MESSAGE = "Invalid API key.";
    public static final String UNAUTHORIZED_API_KEY_ERROR_CODE= "401";

    public static final String TOO_MANY_REQUESTS_MESSAGE = "TOO many request";
    public static final String TOO_MANY_REQUESTS_ERROR_CODE= "401";



}
