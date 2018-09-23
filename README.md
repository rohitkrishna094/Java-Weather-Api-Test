# Java-Weather-Api-Test

## Problem Statement

Create a spring boot app that returns the weather for a given zip code or city. Your app should expose an API with city or zip as parameter and return the weather.

Also expose an api that accepts 20 zip codes from a file and return weather in each place.

Your app should also handle the situation when a zipcode given by the user is not valid. It should throw an exception like NoLocationMatchesException and the output should be
{
"error":"Location not found",
"message":"Location not found with provided city/zipcode"
}

Your api should accept either city or zip code as input parameter example: /api/weather?city=xxx or /api/weather?zip=xxx

For the api which reads zipcodes from file it can be /api/whether/file

---

## Endpoints

- GET https://weather-api-java-homework.herokuapp.com/api/weather?city=london - Here city(london) should be valid name of a city from anywhere in the world or else you will get an error response
- GET https://weather-api-java-homework.herokuapp.com/api/weather?zip=60614 - Here zip should be a valid zipcode(defaults to usa)
- POST https://weather-api-java-homework.herokuapp.com/api/weather/file - This is a post request, where the body should have a file key(parameter) and a real file as a value. The file should contain zipcodes separated by a newline character. Also there cannot be more than 20 zipcodes in
  a file. Make sure each zipcode is a valid us zip code. Test this using curl or postman etc.

---

## Local Build Instructions

If you want to run this project locally, follow these instructions

- git clone this repo
- Open terminal/command line tool and go to the current directory
- Go to WeatherService.java file and make sure to add apiKey where it says

        private String apiKey ="";

- Run ./mvn clean install (if mvn not in path use ./mvnw)
- Run ./mvn spring-boot:run (if mvn not in path use ./mvnw)
- Test the endpoints given above using curl or postman etc. Except use localhost:8080/ as your base url instead of the above herokuapp url.

---

## Run Tests

- Run TestRunner.java as java application. This file is present in src/test/java/com/example/demo/ folder.
