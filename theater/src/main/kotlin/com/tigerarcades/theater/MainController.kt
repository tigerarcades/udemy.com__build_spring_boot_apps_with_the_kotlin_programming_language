package com.tigerarcades.theater

import com.tigerarcades.theater.data.PerformanceRepository
import com.tigerarcades.theater.data.SeatRepository
import com.tigerarcades.theater.domain.Booking
import com.tigerarcades.theater.domain.Performance
import com.tigerarcades.theater.domain.Seat
import com.tigerarcades.theater.services.BookingService
import com.tigerarcades.theater.services.TheaterService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.servlet.ModelAndView

@Controller
class MainController {

    @Autowired
    private lateinit var bookingService: BookingService

    @Autowired
    private lateinit var performanceRepository: PerformanceRepository

    @Autowired
    private lateinit var theaterService: TheaterService

    @Autowired
    private lateinit var seatRepository: SeatRepository

    @RequestMapping("")
    fun homepage(): ModelAndView {
        val model = defaultMapModel(CheckAvailabilityBackingBean())
        return ModelAndView(
            "seatBooking",
            model
        )
    }

    private fun defaultMapModel(bean: CheckAvailabilityBackingBean): Map<String, Any> {
        return mapOf(
            "bean" to bean,
            "performances" to performanceRepository.findAll(),
            "seatNums" to 1..36,
            "seatRows" to 'A'..'O'
        )
    }

    @RequestMapping(
        "/checkAvailability",
        method = [RequestMethod.POST]
    )
    fun checkAvailability(bean: CheckAvailabilityBackingBean): ModelAndView {

        val selectedSeat = bookingService.findSeat(bean.selectedSeatNum, bean.selectedSeatRow)!!
        val selectedPerformance = performanceRepository.findById(bean.selectedPerformance!!).get()
        val isSeatFree = bookingService.isSeatFree(selectedSeat, selectedPerformance)

        bean.seat = selectedSeat
        bean.performance = selectedPerformance
        bean.selectedPerformance = selectedPerformance.id
        bean.result = "Seat $selectedSeat is ${if (isSeatFree) "free" else "booked"}"
        bean.available = isSeatFree

        if (!isSeatFree) {
            bean.booking = bookingService.findBooking(selectedSeat, selectedPerformance)
        }

        return ModelAndView(
            "seatBooking",
            defaultMapModel(bean)
        )
    }

    @RequestMapping("booking", method = [org.springframework.web.bind.annotation.RequestMethod.POST])
    fun bookASeat(bean: CheckAvailabilityBackingBean): ModelAndView {
        val selectedPerformanceId: Long? = bean.performance!!.id
        val performance = performanceRepository.findById(selectedPerformanceId!!).get()

        val booking = bookingService.reserveSeat(
            bean.seat!!,
            performance,
            bean.customerName
        )
        return ModelAndView("bookingConfirmed", "booking", booking)
    }

    @RequestMapping("bootstrap")
    fun createInitialData(): ModelAndView {

        val p = Performance(0, "Random Performance")
        performanceRepository.save(p)

        // create data and save to the database
        val seats = theaterService.seats
        seatRepository.saveAll(seats)
        return homepage()
    }
}

class CheckAvailabilityBackingBean {
    var customerName = ""
    var selectedSeatNum = 1
    var selectedSeatRow = 'A'
    var selectedPerformance: Long? = null
    var result = ""

    var available: Boolean? = null
    var seat: Seat? = null
    var performance: Performance? = null
    var booking: Booking? = null
}

