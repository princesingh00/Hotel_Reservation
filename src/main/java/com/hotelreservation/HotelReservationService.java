package com.hotelreservation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class HotelReservationService {

    private static final DateTimeFormatter DATE_RANGE_FORMAT = DateTimeFormatter.ofPattern("ddMMMyyyy");
    private List<Hotel> hotels;

    public HotelReservationService() {
        this.hotels = new ArrayList<>();
    }

    public void greetCustomer() {
        System.out.println("**************************************************************");
        System.out.println("********* Welcome to Online Hotel Reservation Service ********");
        System.out.println("**************************************************************");
    }

    public boolean addHotel(Hotel hotel) {
        return this.hotels.add(hotel);
    }

    public List<Result> findCheapestHotelsBasedOnDay(CustomerType customerType,
                                                     String initialDateRange,
                                                     String endDateRange) {
        LocalDate initialDate = LocalDate.parse(initialDateRange, DATE_RANGE_FORMAT);
        LocalDate endDate = LocalDate.parse(endDateRange, DATE_RANGE_FORMAT);

        List<Result> results = this.hotels.stream()
                .map(hotel -> {
                    Result result = new Result();
                    result.setHotelName(hotel.getName());
                    result.setTotalRate(hotel.getTotalRate(customerType, initialDate, endDate));
                    return result;
                })
                .sorted(Comparator.comparing(Result::getTotalRate))
                .collect(Collectors.toList());

        return results.stream().filter(result -> result.getTotalRate() == results.get(0).getTotalRate())
                .collect(Collectors.toList());
    }

    public List<Result> findCheapestBestRatedHotel(CustomerType customerType,
                                                   String initialDateRange,
                                                   String endDateRange) {
        LocalDate initialDate = LocalDate.parse(initialDateRange, DATE_RANGE_FORMAT);
        LocalDate endDate = LocalDate.parse(endDateRange, DATE_RANGE_FORMAT);

        List<Result> results = this.hotels.stream()
                .map(hotel -> {
                    Result result = new Result();
                    result.setHotelName(hotel.getName());
                    result.setTotalRate(hotel.getTotalRate(customerType, initialDate, endDate));
                    result.setRating(hotel.getRating());
                    return result;
                })
                .sorted(Comparator.comparing(Result::getTotalRate).thenComparing(Result::getRating,
                        Comparator.reverseOrder()))
                .collect(Collectors.toList());

        return results.stream()
                .filter(result ->
                        result.getTotalRate() == results.get(0).getTotalRate()
                                && result.getRating() == results.get(0).getRating())
                .collect(Collectors.toList());
    }

    public List<Result> findBestRatedHotel(CustomerType customerType,
                                           String initialDateRange,
                                           String endDateRange) {
        LocalDate initialDate = this.getParsedDate(initialDateRange);
        LocalDate endDate = this.getParsedDate(endDateRange);

        if (endDate.isBefore(initialDate))
            throw new HotelReservationException(HotelReservationException.
                    ExceptionType.INVALID_DATERANGE);

        List<Result> results = this.hotels.stream()
                .map(hotel -> {
                    Result result = new Result();
                    result.setHotelName(hotel.getName());
                    result.setTotalRate(hotel.getTotalRate(customerType, initialDate, endDate));
                    result.setRating(hotel.getRating());
                    return result;
                })
                .sorted(Comparator.comparing(Result::getRating, Comparator.reverseOrder()))
                .collect(Collectors.toList());

        return results.stream()
                .filter(result -> result.getRating() == results.get(0).getRating())
                .collect(Collectors.toList());
    }

    private LocalDate getParsedDate(String dateString) {
        try {
            return LocalDate.parse(dateString, DATE_RANGE_FORMAT);
        } catch (DateTimeParseException e) {
            throw new HotelReservationException(HotelReservationException
                    .ExceptionType.INVALID_DATE_FORMAT);
        }
    }
}
