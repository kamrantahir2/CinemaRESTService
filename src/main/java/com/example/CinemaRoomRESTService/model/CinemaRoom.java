package com.example.CinemaRoomRESTService.model;

import com.example.CinemaRoomRESTService.Statistics;
import com.example.CinemaRoomRESTService.service.SeatService;
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
//    @JsonProperty("available_seats")
    private List<Seat> printedList;
//    private List<Seat> seatsToPrint;
    @JsonIgnore
    private int earnings;

    private SeatService service;

//    Methods

    public void update() {
        printedList = new ArrayList<>();
        for (int i = 0; i < seatsList.size(); i++) {
            if (!seatsList.get(i).isBooked()) {
                printedList.add(seatsList.get(i));
            }
        }
    }

    public void createRoom() {
        this.seatsList = (List<Seat>) service.getAll();
        int row = 0;
        int tempColumn = 0;
        for (int i = 0; i < totalSeats; i++) {
            Seat temp = new Seat(row + 1, tempColumn + 1);
            seatsList.add(temp);
            temp.setId();
            temp.determinePrice();
            temp.setBooked(false);
            service.save(temp.getRow(), temp.getColumn(), false);
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
                seat.setBooked(true);
                stats.setBookedSeatsCount(stats.getBookedSeatsCount() + 1);
                stats.calculatePercentageBooked();
                earnings += seat.getPrice();
                stats.setEarnings(this.earnings);
                break;
            }
        }

        return booked;
    }

    public boolean returnTicket(Seat seat) {
        boolean returned = false;

        seat.setId();
//        System.out.println("ROW = " + seat.getRow());
//        System.out.println("COL = " + seat.getColumn());


        for (Seat s : seatsList) {
//            System.out.println("ID s = " + s.getId());
            if (seat.getRow() == s.getRow() && seat.getColumn() == s.getColumn()) {
                seat.setBooked(false);
                returned = true;
                seat.determinePrice();
                this.earnings -= seat.getPrice();
                stats.setBookedSeatsCount(stats.getBookedSeatsCount() - 1);
                stats.setEarnings(stats.getEarnings() - seat.getPrice());
                stats.calculatePercentageBooked();
                service.save(seat, false);
                service.updateBooked(seat.getId(), false);
                seatsList = (List<Seat>) service.getAll();
                break;
            }
        }
        return returned;
    }

//    Constructors

    public CinemaRoom(int row, int column, SeatService service) {
        this.total_rows = row;
        this.total_columns = column;
        this.service = service;
        totalSeats = row * column;
        createRoom();
        update();
        stats = new Statistics(totalSeats, this.earnings);
    }


    public CinemaRoom(SeatService service) {
        this.service = service;
    }

//    Getters and Setters

    public Statistics getStats() {
        return stats;
    }

    public void setStats(Statistics stats) {
        this.stats = stats;
    }

    private Statistics stats;

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

    public List<Seat> getPrintedList() {
        return printedList;
    }

    public void setPrintedList(List<Seat> printedList) {
        this.printedList = printedList;
    }
}