# Weather API with GET endpoint to get the weather data for city and country


##Technology used


Spring Boot
Spring Data JPA
Lombok
H2 In memory database
Guava
Gradle
Junit
Mockito



##Design

The microservice is developed in Spring boot. I have used Spring MVC and Spring Data JPA.
The layers of microservice are-

1. Controller - This accepts the GET Request. 
   Versioning added in url to support multiple versions for future enhancements.
   The GET request has 3 params city, country and appid.
   The city and country are names of city and country for which weather data is needed. 
   appid is the API Key.
   Request should be made with valid API KEY
   
2. Service - The service layer of application

ValidationService - Validates the given request. Sends a custom error message with cod and description with HTTP error code.
   The code and message are added in Constants.java class 

WeatherService - It calls the Open Weather API via REST, fetches and saves details in H2 database.
   Required data is then queried and sent back.
   
3. Repository - Used to perform Data Access Operation

4. Model - DTO and Entity(database table created)



##Steps to run

The project is executable. It can be checked out and run.
TO RUN: Run the java/com/sampleproject/weatherapi/WeatherApiApplication.java
The application starts on port 8081(This is the port specified in application.yaml file)
There is one endpoint exposed at part of this sample.
The endpoint is GET endpoint.
Validation is added on the city name, country name and API KEY
The valid API keys created from Open Weather API is present in application.yaml file (api.apiKeys)

##Sample Request


Method : GET
Sample URL: http://localhost:8081/v1/weather?city=Melbourne&country=Australia&appid=521c6bc368f45024e808037bbc5926c5

##TO DO
I have tried implementing throttling using Guava Rate limiter but that limits requests per second and not by api
key(5 requests an hour) but need to spend more time on implementing the correct logic.
We can use other rate limiters like Bucket4j or spring cloud gateway or Zuul.

More unit test cases should be added to increase coverage.




