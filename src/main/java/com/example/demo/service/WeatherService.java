package com.example.demo.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.exception.FileLimitException;
import com.example.demo.exception.NoLocationMatchesException;
import com.example.demo.properties.Properties;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Service
public class WeatherService {

    // Api key for OpenWeatherMap
    private String apiKey = "f8d493c278e827dfd4a0584a9ed3295c";

    // Base Url to get the weather info
    private String baseUrl = "https://api.openweathermap.org/data/2.5/weather";

    // A simple Rest Client to send requests and receive responses
    private OkHttpClient client = new OkHttpClient();

    /*
     * Returns the weather information based on given city or zipcode
     * 
     * @param input This contains either the city name or zipcode depending on cityFlag parameter as described below
     * 
     * @param cityFlag This flag indicates whether the city
     */
    public String getData(String input, boolean cityFlag) throws NoLocationMatchesException {
        String localUrl = baseUrl;
        if (cityFlag) {
            localUrl += "?q=" + input + "&APPID=" + apiKey + "&units=metric";
        } else {
            localUrl += "?zip=" + input + ",us" + "&APPID=" + apiKey + "&units=metric";
        }
        String res = fetch(localUrl);

        // validate and throw exceptions
        Gson gson = new Gson();
        JsonObject body = gson.fromJson(res, JsonObject.class);
        JsonElement jeId = body.get("cod");
        int error = Integer.parseInt(jeId.getAsString());
        if (error == 404 || error == 400) {
            throw new NoLocationMatchesException();
        }
        return res;
    }

    /*
     * Returns the weather information based from the file
     * 
     * @param file This represents the file which should have less than or equal to 20 zipcodes.
     * 
     */
    public String getDataFromFile(MultipartFile file) throws FileLimitException {
        List<String> result = new ArrayList<>();

        // Get a list of zipcodes from the input file
        List<String> list = getZipCodes(file);

        // Check if the number of zipcodes are more than 20
        if (list.size() > 20) {
            throw new FileLimitException();
        }

        // Get the corresponding responses and fill them in result ArrayList
        for (int i = 0; i < list.size(); i++) {

            // Validate for invalid inputs
            try {
                result.add(getData(list.get(i), false));
            } catch (NoLocationMatchesException e) {
                e.printStackTrace();
                // Return error response if invalid input
                result.add(Properties.errorResponse);
            }
        }
        return result.toString();
    }

    /*
     * Returns a List of zipcodes from given file
     * 
     * @param file This represents the file which should have less than or equal to 20 zipcodes.
     * 
     */
    private List<String> getZipCodes(MultipartFile file) {
        List<String> list = new ArrayList<>();
        String res = "";
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                res = new String(bytes);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        String[] split = res.split("\n");
        for (String s : split) {
            list.add(s);
        }
        return list;
    }

    /*
     * Returns a response from a given url
     * 
     * @param file This represents the file which should have less than or equal to 20 zipcodes.
     * 
     */
    private String fetch(String url) {
        Request request = new Request.Builder().url(url).build();
        String result = "";
        try {
            Response response = client.newCall(request).execute();
            result = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
