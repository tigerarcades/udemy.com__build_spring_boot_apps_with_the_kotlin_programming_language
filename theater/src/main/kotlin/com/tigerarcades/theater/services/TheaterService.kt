package com.tigerarcades.theater.services

import com.tigerarcades.theater.domain.Seat
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class TheaterService {

    /*
     * SEAT PRICES:
     * seats in row 14 and 15 cost 14,50
     * seats in row 1 to 13 and numbered 1 to 13 or 34 to 36 cost 16,00
     * all other seats in row 1 cost 21.00
     * all other seats cost 18,00
     *
     * SEAT DESCRIPTIONS:
     * seats in row 15: Back row
     * seats in row 14: Cheaper seat
     * seats in row 1 to 3 and 34 to 36: Restricted View
     * all other seats in rows 1 and 2: Best view
     * all other seats: Standard seat
     */
    enum class SeatType(
        val price: BigDecimal,
        val description: String
    ) {
        SEAT_TYPE_BEST_VIEW(BigDecimal(21.50), "Best view"),
        SEAT_TYPE_STANDARD_SEAT(BigDecimal(18.00), "Standard seat"),
        SEAT_TYPE_RESTRICTED(BigDecimal(16.50), "Restricted"),
        SEAT_TYPE_CHEAPER_SEAT(BigDecimal(14.50), "Cheaper seat"),
        SEAT_TYPE_BACK_ROW(BigDecimal(14.50), "Back row");

        companion object {
            fun getSeatOrder(): List<Seat> {
                val list = ArrayList<Seat>()
                for (row in 'A'..'O') {
                    for (num in 1..36) {
                        val seatType: SeatType = when {
                            row == 'O' -> SEAT_TYPE_BACK_ROW
                            row == 'N' -> SEAT_TYPE_CHEAPER_SEAT
                            (row in 'A'..'B') && (num in 4..33) -> SEAT_TYPE_BEST_VIEW
                            (row in 'C'..'M') && num !in 4..33 -> SEAT_TYPE_RESTRICTED
                            else -> SEAT_TYPE_STANDARD_SEAT
                        }
                        list.add(Seat(0, row, num, seatType.price, seatType.description))
                    }
                }
                return list.toList()
            }
        }
    }

    val seats: List<Seat>
        get() = SeatType.getSeatOrder().toList()

    fun find(num: Int, row: Char): Seat {
        return seats.first { it.row == row && it.num == num }
    }
}
