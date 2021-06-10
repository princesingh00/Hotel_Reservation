package com.hotelreservation;

public class Rate implements Comparable{
    private Integer weekendRate;
    private Integer weekdayRate;

    public Rate(int weekdayRate, int weekendRate) {
        this.weekdayRate = weekdayRate;
        this.weekendRate = weekendRate;
    }

    public int getWeekendRate() {
        return weekendRate;
    }

    public Integer getWeekdayRate() {
        return weekdayRate;
    }

    @Override
    public int compareTo(Object o) {
        Rate otherObject = (Rate) o;
        return this.weekdayRate.compareTo(otherObject.getWeekdayRate());
    }
}
