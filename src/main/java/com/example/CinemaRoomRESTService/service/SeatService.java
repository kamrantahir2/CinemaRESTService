package com.example.CinemaRoomRESTService.service;

import com.example.CinemaRoomRESTService.model.Seat;
import com.example.CinemaRoomRESTService.repository.SeatRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SeatService {

   private final SeatRepository seatRepository;


    public SeatService(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }

    public Iterable<Seat> get() {
        return seatRepository.findAll();
    }

    public Seat get(Integer id) {
        return seatRepository.findById(id).orElse(null);
    }

    public void updateBooked(int id, boolean bool) {
        Optional<Seat> opt = Optional.of(get(id));

        if (opt.isPresent()) {
            Seat seat = opt.get();
            seat.setBooked(bool);
        }
    }

    public Seat save(int row, int column) {
        Seat seat = new Seat();
        seat.setRow(row);
        seat.setColumn(column);
        seat.setId();
        seat.determinePrice();
        seatRepository.save(seat);
        return seat;
    }

}

