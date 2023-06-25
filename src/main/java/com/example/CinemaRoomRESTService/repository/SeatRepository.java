package com.example.CinemaRoomRESTService.repository;

import com.example.CinemaRoomRESTService.model.Seat;
import org.springframework.data.domain.Sort;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SeatRepository  extends CrudRepository<Seat, Integer> {

        List<Seat> findByBookedIsNot(boolean bool);
}
