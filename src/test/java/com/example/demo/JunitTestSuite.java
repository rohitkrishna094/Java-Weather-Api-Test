package com.example.demo;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses({ WeatherServiceTest.class, WeatherControllerTest.class })

public class JunitTestSuite {

}
