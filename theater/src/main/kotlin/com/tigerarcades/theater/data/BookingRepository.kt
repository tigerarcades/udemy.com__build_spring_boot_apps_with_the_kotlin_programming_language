package com.tigerarcades.theater.data

import com.tigerarcades.theater.domain.Booking
import org.springframework.data.jpa.repository.JpaRepository

interface BookingRepository: JpaRepository<Booking, Long>