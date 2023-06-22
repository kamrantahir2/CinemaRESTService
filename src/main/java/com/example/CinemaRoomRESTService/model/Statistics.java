package com.example.CinemaRoomRESTService.model;

public class Statistics {
    private int totalSeats;
    private int bookedSeatsCount = 0;
    private int earnings;
    private float percentageBooked = 0;

    public void calculatePercentageBooked() {
        if (bookedSeatsCount > 0) {
            this.percentageBooked = ((float) bookedSeatsCount / totalSeats) * 100;
        } else {
            this.percentageBooked = 0;
        }
    }

//    Constructors

    public Statistics(int totalSeats, int earnings) {
        this.totalSeats = totalSeats;
        calculatePercentageBooked();
        this.earnings = earnings;
    }

    public Statistics() {

    }

//    Getters and Setters

    public int getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(int totalSeats) {
        this.totalSeats = totalSeats;
    }

    public int getBookedSeatsCount() {
        return bookedSeatsCount;
    }

    public void setBookedSeatsCount(int bookedSeatsCount) {
        this.bookedSeatsCount = bookedSeatsCount;
        calculatePercentageBooked();
    }

    public int getEarnings() {
        return earnings;
    }

    public void setEarnings(int earnings) {
        this.earnings = earnings;
    }

    public float getPercentageBooked() {
        return percentageBooked;
    }

    public void setPercentageBooked(float percentageBooked) {
        this.percentageBooked = percentageBooked;
    }

}