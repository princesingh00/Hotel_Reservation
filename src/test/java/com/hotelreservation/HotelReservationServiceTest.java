package com.hotelreservation;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class HotelReservationServiceTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    private HotelReservationService hotelReservationService;
    private Hotel lakewood;
    private Hotel bridgewood;
    private Hotel ridgewood;

    @Before
    public void setUp() throws Exception {
        hotelReservationService = new HotelReservationService();

        HashMap<CustomerType, Rate> customerTypeRateMap = new HashMap<>();
        customerTypeRateMap.put(CustomerType.REGULAR, new Rate(110, 90));
        customerTypeRateMap.put(CustomerType.REWARD, new Rate(80, 80));
        lakewood = new Hotel("Lakewood", 3, customerTypeRateMap);

        customerTypeRateMap = new HashMap<>();
        customerTypeRateMap.put(CustomerType.REGULAR, new Rate(160, 40));
        customerTypeRateMap.put(CustomerType.REWARD, new Rate(110, 50));
        bridgewood = new Hotel("Bridgewood", 4, customerTypeRateMap);

        customerTypeRateMap = new HashMap<>();
        customerTypeRateMap.put(CustomerType.REGULAR, new Rate(220, 150));
        customerTypeRateMap.put(CustomerType.REWARD, new Rate(100, 40));
        ridgewood = new Hotel("Ridgewood", 5, customerTypeRateMap);
        hotelReservationService.addHotel(lakewood);
        hotelReservationService.addHotel(bridgewood);
        hotelReservationService.addHotel(ridgewood);
    }

    @Test
    public void whenCalledDisplayMethod_shouldDisplayWelcomeMessage() {
        hotelReservationService.greetCustomer();
    }

    @Test
    public void givenHotel_whenInvokedAddHotel_shouldBeAbleToAdd() {
        assertTrue(hotelReservationService.addHotel(lakewood));
    }

    @Test
    public void givenHotel_shouldAbleToAddRateAccordingToDay() {
        assertTrue(hotelReservationService.addHotel(lakewood));
    }

    @Test
    public void givenDateRange_whenSearched_shouldReturnCheapestHotelBasedOnWeekdayAndWeekend() {
        List<Result> cheapestHotelResult =
                hotelReservationService.findCheapestHotelsBasedOnDay(CustomerType.REGULAR,
                        "11Jun2021", "12Jun2021");

        assertEquals(2, cheapestHotelResult.size());
        assertEquals(200, cheapestHotelResult.get(0).getTotalRate());
    }

    @Test
    public void givenRating_shouldBeAbleToAddRatingForHotel() {
        assertTrue(hotelReservationService.addHotel(lakewood));
    }

    @Test
    public void givenDateRange_whenSearched_shouldReturnCheapestBestRatedHotel() {
        List<Result> cheapestBestHotels =
                hotelReservationService.findCheapestBestRatedHotel(CustomerType.REGULAR,
                        "11Jun2021", "12Jun2021");

        assertEquals(1, cheapestBestHotels.size());
        assertEquals(200, cheapestBestHotels.get(0).getTotalRate());
        assertEquals(4, cheapestBestHotels.get(0).getRating());
    }

    @Test
    public void givenDateRange_whenSearched_shouldReturnBestRatedHotel() {
        List<Result> bestRatedHotels =
                hotelReservationService.findBestRatedHotel(CustomerType.REGULAR,
                        "11Jun2021", "12Jun2021");

        assertEquals(1, bestRatedHotels.size());
        assertEquals(370, bestRatedHotels.get(0).getTotalRate());
        assertEquals(5, bestRatedHotels.get(0).getRating());
    }

    @Test
    public void givenHotelWithRewardRate_shouldBeAbleToAdd() {
        HashMap<CustomerType, Rate> customerTypeRateMap = new HashMap<>();
        customerTypeRateMap.put(CustomerType.REGULAR, new Rate(110, 90));
        customerTypeRateMap.put(CustomerType.REWARD, new Rate(80, 80));

        Hotel lakewood = new Hotel("Lakewood", 3, customerTypeRateMap);

        assertTrue(hotelReservationService.addHotel(lakewood));
    }

    @Test
    public void givenDateRangeAndCustomerType_whenSearched_shouldReturnCheapestBestRatedHotel() {

        List<Result> bestRatedHotel = hotelReservationService.findBestRatedHotel(CustomerType.REWARD,
                "11Jun2021", "12Jun2021");

        assertEquals(1, bestRatedHotel.size());
        assertEquals("Ridgewood", bestRatedHotel.get(0).getHotelName());
        assertEquals(140, bestRatedHotel.get(0).getTotalRate());
        assertEquals(5, bestRatedHotel.get(0).getRating());
    }

    @Test
    public void givenInvalidDateRange_whenSearched_shouldThrowException() {
        expectedException.expect(HotelReservationException.class);
        expectedException.expectMessage(HotelReservationException.ExceptionType.INVALID_DATERANGE.message);
        hotelReservationService.findBestRatedHotel(CustomerType.REGULAR,
                "12Jun2021", "11Jun2021");
    }

    @Test
    public void givenInvalidDateRangeFormat_shouldThrowException() {
        expectedException.expect(HotelReservationException.class);
        expectedException.expectMessage(HotelReservationException.ExceptionType.INVALID_DATE_FORMAT.message);
        hotelReservationService.findBestRatedHotel(CustomerType.REGULAR,
                "11-Jun-2021", "12Jun2021");
    }

    @Test
    public void givenDateRange_whenSearchedForRegularCustomer_shouldReturnCheapestBestRatedHotel() {

        List<Result> cheapestBestRatedHotel = hotelReservationService.findCheapestBestRatedHotel(CustomerType.REGULAR,
                "11Jun2021", "12Jun2021");

        assertEquals(1, cheapestBestRatedHotel.size());
        assertEquals("Bridgewood", cheapestBestRatedHotel.get(0).getHotelName());
        assertEquals(4, cheapestBestRatedHotel.get(0).getRating());
        assertEquals(200, cheapestBestRatedHotel.get(0).getTotalRate());
    }
}
