package com.hotelreservation;

public class HotelReservationException extends RuntimeException{

    public ExceptionType exceptionType;

    public enum ExceptionType{
        INVALID_DATERANGE("Enter proper date range."),
        INVALID_DATE_FORMAT("Enter date in proper format of ddMMMYYYY");

        public String message;

        ExceptionType(String message) {
            this.message = message;
        }
    }

    public HotelReservationException(ExceptionType exceptionType) {
        super(exceptionType.message);
        this.exceptionType = exceptionType;
    }
}
