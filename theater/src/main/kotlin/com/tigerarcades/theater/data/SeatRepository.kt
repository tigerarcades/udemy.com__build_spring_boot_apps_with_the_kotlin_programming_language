package com.tigerarcades.theater.data

import com.tigerarcades.theater.domain.Seat
import org.springframework.data.jpa.repository.JpaRepository

interface SeatRepository : JpaRepository<Seat, Long>