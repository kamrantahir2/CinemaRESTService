package com.example.CinemaRoomRESTService.repository;

import com.example.CinemaRoomRESTService.model.Seat;
import org.springframework.data.repository.CrudRepository;

public interface SeatRepository  extends CrudRepository<Seat, Integer> {
}
