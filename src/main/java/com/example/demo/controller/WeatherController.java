package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.exception.FileLimitException;
import com.example.demo.exception.NoLocationMatchesException;
import com.example.demo.properties.Properties;
import com.example.demo.service.WeatherService;

/**
 * @author Rohit Krishna Marneni
 * 
 *         Weather API with city, zipcode and file as parameters
 * 
 *         Dependencies: Gson for JSON parsing, spring boot for rest api and spring devtools for hot module replacement during debugging/development
 *
 */
@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    /*
     * API Endpoint GET: /api/weather
     * 
     * Params: city
     * 
     * Example Run: localhost:8080/api/weather?city=london
     * 
     * Returns weather information about the given city or an error response if city is invalid
     */
    @GetMapping(value = "", params = "city")
    public String weatherByCity(@RequestParam(value = "city", required = true) String city) {
        String result = "";
        try {
            result = weatherService.getData(city, true);
        } catch (NoLocationMatchesException e) {
            e.printStackTrace();
            result = Properties.errorResponse;
        }
        return result;
    }

    /*
     * API Endpoint GET: /api/weather
     * 
     * Params: zip
     * 
     * Example Run: localhost:8080/api/weather?zip=48335
     * 
     * Returns weather information about the given city or an error response if zipcode is invalid
     * 
     * Each zipcode must be separated by \n(newline) character without any other characters
     */
    @GetMapping(value = "", params = "zip")
    public String weatherByZipcode(@RequestParam(value = "zip", required = true) String zip) {
        String result = "";
        try {
            result = weatherService.getData(zip, false);
        } catch (NoLocationMatchesException e) {
            e.printStackTrace();
            result = Properties.errorResponse;
        }
        return result;
    }

    /*
     * API Endpoint POST: /api/weather/file
     * 
     * Params: file
     * 
     * Example Run: localhost:8080/api/weather/file (with file as a parameter)
     * 
     * Returns weather information(in json array) for all the zipcodes. If any zipcode is invalid, that particular entry in the response has a fileErrorResponse(as part of
     * NoLocationMatchesException).If the number of zipcodes in the given file exceeds 20, then an error response is given(as part of FileLimitException)
     */
    @PostMapping(value = "/file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        String result = "";
        try {
            result = weatherService.getDataFromFile(file);
        } catch (FileLimitException e) {
            e.printStackTrace();
            result = Properties.fileErrorResponse;
        }
        return result;
    }

}
