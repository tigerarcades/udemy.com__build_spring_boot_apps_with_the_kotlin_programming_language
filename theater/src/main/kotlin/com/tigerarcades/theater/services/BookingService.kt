package com.tigerarcades.theater.services

import com.tigerarcades.theater.domain.Seat
import org.springframework.stereotype.Service

@Service
class BookingService {

    fun istSeatFree(seat: Seat): Boolean = true
}