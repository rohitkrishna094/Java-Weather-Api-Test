package com.example.demo.exception;

// This exception is thrown when the input file contains more than 20 zipcodes(i.e., more than 20 lines)
public class FileLimitException extends Exception {
    public FileLimitException() {
        super("File has more than 20 zipcodes");
    }
}
