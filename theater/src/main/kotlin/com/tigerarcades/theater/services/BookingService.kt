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
        return bookingRepository.findAll().none { it.seat == seat && it.performance == performance }
    }

    fun findSeat(seatNum: Int, seatRow: Char): Seat? {
        return seatRepository.findAll().firstOrNull { it.num == seatNum && it.row == seatRow }
    }

    fun reserveSeat(seat: Seat, performance: Performance, customerName: String):Booking {
        val b = Booking(0, customerName)
        b.performance = performance
        b.seat = seat
        bookingRepository.save(b)
        return b
    }

    fun findBooking(selectedSeat: Seat, selectedPerformance: Performance): Booking? {
        return bookingRepository.findAll().first { it.seat == selectedSeat && it.performance == selectedPerformance }
    }
}