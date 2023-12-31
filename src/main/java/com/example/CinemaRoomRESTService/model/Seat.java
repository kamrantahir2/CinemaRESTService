package com.example.CinemaRoomRESTService.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity(name="seat")
@Table(name="seats")
public class Seat {

    @Id
    private int id;

    @Column(name="row")
    private int row;

    @Column(name="col")
    private int column;
    private int price;

//    @JsonIgnore
    private boolean booked;

    public void determinePrice() {
        if (this.row <= 4) {
            this.price = 10;
        } else {
            this.price = 8;
        }
    }

    public void setId() {
        String temp = "";
        temp += this.row;
        temp += this.column;
        this.id = Integer.parseInt(temp);
    }

//    Constructors

    public Seat(int row, int column) {
        this.row = row;
        this.column = column;
        setId();
    }

    public Seat() {
    }

//    Getters and Setters

    public boolean isBooked() {
        return booked;
    }

    public void setBooked(boolean booked) {
        this.booked = booked;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }


    public int getId() {
        return id;
    }

}