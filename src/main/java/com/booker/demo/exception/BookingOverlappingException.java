package com.booker.demo.exception;

public class BookingOverlappingException extends RuntimeException {

    public BookingOverlappingException(String message) {
        super(message);
    }
}
