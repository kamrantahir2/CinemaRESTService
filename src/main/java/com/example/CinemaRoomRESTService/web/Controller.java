package com.example.CinemaRoomRESTService.web;

import com.example.CinemaRoomRESTService.Exceptions;
import com.example.CinemaRoomRESTService.model.CinemaRoom;
import com.example.CinemaRoomRESTService.model.Seat;
import com.example.CinemaRoomRESTService.service.SeatService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class Controller {
    private final SeatService service;

    CinemaRoom cr;
    ObjectMapper objectMapper = new ObjectMapper();

    public Controller(SeatService service) {
        this.service = service;
        cr = new CinemaRoom(9, 9, service);
    }

    //    Basic GetMapping to test the root route
    @GetMapping("/seats")
    public String returnJson() throws JsonProcessingException {
        cr.setPrintedList(cr.sortList(cr.getPrintedList()));
        return objectMapper.writeValueAsString(cr);
    }

    //    PostMapping used to book seats
    @PostMapping("/purchase")
    public ResponseEntity<String> bookSeat(@RequestBody Seat seat) throws JsonProcessingException {

        boolean booked = cr.purchaseSeat(seat);
        if (booked) {
            seat.determinePrice();
            seat.setBooked(true);
            seat.setId();
            service.updateBooked(seat.getId(), true);
            service.save(seat, true);
            cr.setSeatsList((List<Seat>) service.get());

            String returnedJson = objectMapper.writeValueAsString(seat);
            cr.update();
            cr.setPrintedList(cr.sortList(cr.getPrintedList()));
            return new ResponseEntity<>(returnedJson, HttpStatus.OK);
        } else {
            String e;
            if (seat.getRow() > cr.getTotal_rows() || seat.getColumn() > cr.getTotal_columns() || seat.getRow() < 1 || seat.getColumn() < 1) {
                Exceptions exceptions = new Exceptions("The number of a row or a column is out of bounds!");
                e = objectMapper.writeValueAsString(exceptions);
            } else {
                Exceptions exceptions = new Exceptions("The ticket has been already purchased!");
                e = objectMapper.writeValueAsString(exceptions);
            }
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }

    }

//   Post Mapping used to return tickets
    @PostMapping("/return")
    public ResponseEntity<String> returnTicket(@RequestBody Seat seat) throws JsonProcessingException {
        boolean returned = cr.returnTicket(seat);
        if (returned) {
            seat = service.save(seat, false);
            cr.update();
            cr.setPrintedList(cr.sortList(cr.getPrintedList()));
            String returnedJson = objectMapper.writeValueAsString(seat);
            return new ResponseEntity<>(returnedJson, HttpStatus.OK);
        } else {
            Exceptions exceptions = new Exceptions("This ticket has not been booked");
            String e = objectMapper.writeValueAsString(exceptions);
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }

    }

}