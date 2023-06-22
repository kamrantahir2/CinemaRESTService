package com.example.CinemaRoomRESTService;

public class Exceptions {
    String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Exceptions(String error) {
        this.error = error;
    }

    public Exceptions() {
    }
}