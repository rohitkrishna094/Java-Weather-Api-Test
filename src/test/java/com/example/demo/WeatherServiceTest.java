package com.example.demo;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.exception.FileLimitException;
import com.example.demo.exception.NoLocationMatchesException;
import com.example.demo.service.WeatherService;
import com.jayway.jsonpath.JsonPath;

import net.minidev.json.JSONArray;

public class WeatherServiceTest {

    private WeatherService weatherService;

    private MultipartFile mockMultipartFileCorrect;
    private MultipartFile mockMultipartFileWrong;

    @Before
    public void setup() {
        weatherService = new WeatherService();
        byte[] correctContent = TestUtil.zipsCorrectContent.getBytes(Charset.forName("UTF-8"));
        byte[] wrongContent = TestUtil.zipsWrongContent.getBytes(Charset.forName("UTF-8"));
        mockMultipartFileCorrect = new MockMultipartFile("zips.txt", "zips.txt", "text/plain", correctContent);
        mockMultipartFileWrong = new MockMultipartFile("zips.txt", "zips.txt", "text/plain", wrongContent);
    }

    // Check to see if returned json has certain elements in it(according to openweathermap api) for city
    @Test
    public void getData_shouldReturnCorrectResponse_city() throws Exception {
        String json = weatherService.getData("london", true);

        // Check whether there are 12 keys in json
        Map<String, String> objectMap = JsonPath.read(json, "$");
        assertEquals(12, objectMap.size());

        // Check whether keys are in certain order
        List<String> keys = getAllKeys(objectMap);
        assertTrue(jsonIsValid(keys));
    }

    // Check to see if returned json has certain elements in it(according to openweathermap api) for zipcodes
    @Test
    public void getData_shouldReturnCorrectResponse_zip() throws Exception {
        String json = weatherService.getData("48335", false);

        // Check whether there are 12 keys in json
        Map<String, String> objectMap = JsonPath.read(json, "$");
        assertEquals(12, objectMap.size());

        // Check whether keys are in certain order
        List<String> keys = getAllKeys(objectMap);
        assertTrue(jsonIsValid(keys));
    }

    // Check to see if returned json has certain elements in it(according to openweathermap api) for file
    @Test
    public void getData_shouldReturnCorrectResponse_file() throws Exception {
        assertTrue(checkFileResponse(mockMultipartFileCorrect, 20, true));
    }

    // Check to see if returned json from file throws an exception for more than 20 zipcodes
    @Test
    public void getData_shouldThrowException_file() {
        try {
            weatherService.getDataFromFile(mockMultipartFileWrong);
        } catch (FileLimitException e) {
            assertThat(e.getMessage(), is("File has more than 20 zipcodes"));
        }
    }

    // Check to see if returned json for city throws an exception for invalid cityname
    @Test
    public void getData_shouldThrowException_city() throws Exception {
        try {
            weatherService.getData("ny", true);
        } catch (NoLocationMatchesException e) {
            assertThat(e.getMessage(), is("Invalid city or zipcode"));
        }
    }

    // Check to see if returned json for zipcode throws an exception for invalid zipcode
    @Test
    public void getData_shouldThrowException_zip() throws Exception {
        try {
            weatherService.getData("4833", false);
        } catch (NoLocationMatchesException e) {
            assertThat(e.getMessage(), is("Invalid city or zipcode"));
        }
    }

    private boolean checkFileResponse(MultipartFile mockMultiPartFile, int numberOfZips, boolean assertFlag) throws NoLocationMatchesException, FileLimitException {
        mockMultiPartFile = mockMultipartFileCorrect;
        String json = weatherService.getDataFromFile(mockMultiPartFile);

        // Check whether there are 20 elements in jsonArray i.e., for 20 zipcodes
        JSONArray jsonArray = JsonPath.read(json, "$");
        if (assertFlag)
            assertEquals(numberOfZips, jsonArray.size());

        boolean flag = false;
        // Check whether they are in certain order
        for (int i = 0; i < jsonArray.size(); i++) {
            Object entry = jsonArray.get(i);
            Map<String, String> objectMap = JsonPath.read(entry.toString(), "$");
            List<String> keys = getAllKeys(objectMap);
            if (!jsonIsValid(keys)) {
                flag = true;
                break;
            }
        }
        return flag == true;
    }

    private boolean jsonIsValid(List<String> keys) {
        return keys.equals(TestUtil.keysToBeFound);
    }

    private List<String> getAllKeys(Map<String, String> map) {
        List<String> list = new ArrayList<>();
        for (String k : map.keySet()) {
            list.add(k);
        }
        Collections.sort(list);
        return list;
    }

}
