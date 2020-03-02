package com.tigerarcades.theater.services

import com.tigerarcades.theater.data.BookingRepository
import com.tigerarcades.theater.data.SeatRepository
import com.tigerarcades.theater.domain.Booking
import com.tigerarcades.theater.domain.Performance
import com.tigerarcades.theater.domain.Seat
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class BookingService {

    @Autowired
    lateinit var bookingRepository: BookingRepository

    @Autowired
    lateinit var seatRepository: SeatRepository


    fun isSeatFree(seat: Seat, performance: Performance): Boolean {
        return findBooking(seat, performance) == null
    }

    fun findSeat(seatNum: Int, seatRow: Char): Seat? {
        return seatRepository.findAll().firstOrNull { it.num == seatNum && it.row == seatRow }
    }

    fun reserveSeat(seat: Seat, performance: Performance, customerName: String): Booking {
        val booking = Booking(0, customerName)
        booking.performance = performance
        booking.seat = seat
        bookingRepository.save(booking)
        return booking
    }

    fun findBooking(seat: Seat, performance: Performance): Booking? {
        return bookingRepository.findAll()
            .firstOrNull() { it.seat == seat && it.performance == performance }
    }
}