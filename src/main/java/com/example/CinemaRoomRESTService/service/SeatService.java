package com.example.CinemaRoomRESTService.service;

import com.example.CinemaRoomRESTService.model.Seat;
import com.example.CinemaRoomRESTService.repository.SeatRepository;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public Iterable<Seat> getAll() {
        return seatRepository.findByBookedIsNot(true);
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

    public Seat save(int row, int column, boolean bool) {
        Seat seat = new Seat();
        seat.setRow(row);
        seat.setColumn(column);
        seat.setBooked(bool);
        seat.setId();
        seat.determinePrice();
        seatRepository.save(seat);
        return seat;
    }

    public Seat save(Seat seat, boolean bool) {
        seat.setId();
        seat.setBooked(bool);
        seatRepository.save(seat);
        return seat;
    }

}

