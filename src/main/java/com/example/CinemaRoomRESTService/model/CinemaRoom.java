package com.example.CinemaRoomRESTService.model;

import com.example.CinemaRoomRESTService.Statistics;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class CinemaRoom {
    private int total_rows;
    private int total_columns;
    private int totalSeats;
    @JsonIgnore
    private List<Seat> seatsList;
    @JsonProperty("available_seats")
    private List<Seat> seatsToPrint;
    @JsonIgnore
    private int earnings;

//    Methods

    public void createRoom() {
        this.seatsList = new ArrayList<>();
        int row = 0;
        int tempColumn = 0;
        for (int i = 0; i < totalSeats; i++) {
            Seat temp = new Seat(row + 1, tempColumn + 1);
            seatsList.add(temp);
            tempColumn ++;
            if (tempColumn + 1 > total_columns) {
                tempColumn = 0;
                row ++;
            }
        }

    }

    public void addToList(Seat seat) {
        this.seatsList.add(seat);
    }

    public boolean purchaseSeat(Seat seat) {
        boolean booked = false;

        for (int i = 0; i < seatsList.size(); i++) {
            Seat currentSeat = seatsList.get(i);
            if (currentSeat.getRow() == seat.getRow() && currentSeat.getColumn() == seat.getColumn()) {
                booked = true;
                seat.determinePrice();
                currentSeat.setBooked(true);
                stats.setBookedSeatsCount(stats.getBookedSeatsCount() + 1);
                stats.calculatePercentageBooked();
                earnings += seat.getPrice();
                stats.setEarnings(this.earnings);
                break;
            }
        }
        printSeatsToPrint();
        return booked;
    }

    public void printSeatsToPrint() {
        seatsToPrint = new ArrayList<>();

        for (Seat seat : seatsList) {
            if (!seat.isBooked()) {
                seatsToPrint.add(seat);
            }
        }

    }

    public boolean returnTicket(Seat seat) {
        boolean returned = false;

        for (Seat s : seatsList) {
            if (seat.getRow() == s.getRow() && seat.getColumn() == s.getColumn()) {
                s.setBooked(false);
                returned = true;
                seat.determinePrice();
                this.earnings -= seat.getPrice();
                stats.setBookedSeatsCount(stats.getBookedSeatsCount() - 1);
                stats.setEarnings(stats.getEarnings() - seat.getPrice());
                stats.calculatePercentageBooked();
                break;
            }
        }

        printSeatsToPrint();
        return returned;
    }

//    Constructors

    public CinemaRoom(int row, int column) {
        this.total_rows = row;
        this.total_columns = column;
        totalSeats = row * column;
        createRoom();
        printSeatsToPrint();
        stats = new Statistics(totalSeats, this.earnings);
    }

    public CinemaRoom() {
    }

//    Getters and Setters

    public Statistics getStats() {
        return stats;
    }

    public void setStats(Statistics stats) {
        this.stats = stats;
    }

    private Statistics stats;

    public List<Seat> getSeatsToPrint() {
        return seatsToPrint;
    }

    public void setSeatsToPrint(List<Seat> seatsToPrint) {
        this.seatsToPrint = seatsToPrint;
    }

    public List<Seat> getSeatsList() {
        return seatsList;
    }

    public void setSeatsList(List<Seat> seatsList) {
        this.seatsList = seatsList;
    }

    public int getTotal_rows() {
        return total_rows;
    }

    public void setTotal_rows(int row) {
        this.total_rows = row;
    }

    public int getTotal_columns() {
        return total_columns;
    }

    public void setTotal_columns(int column) {
        this.total_columns = column;
    }

    public int getEarnings() {
        return earnings;
    }

    public void setEarnings(int earnings) {
        this.earnings = earnings;
    }
}