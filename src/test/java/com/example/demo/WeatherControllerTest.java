package com.example.demo;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.demo.controller.WeatherController;
import com.example.demo.properties.Properties;
import com.example.demo.service.WeatherService;

public class WeatherControllerTest {

    private MockMvc mockMvc;

    @Mock
    private WeatherService weatherService;

    @InjectMocks
    private WeatherController weatherController;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(weatherController).build();
    }

    @Test
    public void weatherByCity_shouldReturnValidResponse_city() throws Exception {
        when(weatherService.getData("london", true)).thenReturn(TestUtil.londonCorrectResponse);

        MvcResult result = mockMvc.perform(get("/api/weather?city=london")).andExpect(status().isOk()).andReturn();
        String content = result.getResponse().getContentAsString();

        assertEquals("London response should be valid", TestUtil.londonCorrectResponse, content);
    }

    @Test
    public void weatherByCity_shouldReturnErrorResponse_city() throws Exception {
        when(weatherService.getData("lo", true)).thenReturn(Properties.errorResponse);

        MvcResult result = mockMvc.perform(get("/api/weather?city=lo")).andExpect(status().isOk()).andReturn();
        String content = result.getResponse().getContentAsString();

        assertEquals("London response should be invalid", Properties.errorResponse, content);
    }

    @Test
    public void weatherByCity_shouldReturnValidResponse_zip() throws Exception {
        // 60614 - Chicago Zipcode
        when(weatherService.getData("60614", false)).thenReturn(TestUtil.ChicagoCorrectResponse);

        MvcResult result = mockMvc.perform(get("/api/weather?zip=60614")).andExpect(status().isOk()).andReturn();
        String content = result.getResponse().getContentAsString();

        assertEquals("Chicago response should be valid", TestUtil.ChicagoCorrectResponse, content);
    }

    @Test
    public void weatherByCity_shouldReturnErrorResponse_zip() throws Exception {
        // 6061 - Invalid Zipcode
        when(weatherService.getData("6061", false)).thenReturn(Properties.errorResponse);

        MvcResult result = mockMvc.perform(get("/api/weather?zip=6061")).andExpect(status().isOk()).andReturn();
        String content = result.getResponse().getContentAsString();

        assertEquals("Chicago response should be invalid", Properties.errorResponse, content);
    }

}
