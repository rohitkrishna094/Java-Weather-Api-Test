package com.example.demo.exception;

// This exception is thrown when the given city or zipcode is invalid(in terms with openweather api that is being used in this application)
public class NoLocationMatchesException extends Exception {
    public NoLocationMatchesException() {
        super("Invalid city or zipcode");
    }
}
