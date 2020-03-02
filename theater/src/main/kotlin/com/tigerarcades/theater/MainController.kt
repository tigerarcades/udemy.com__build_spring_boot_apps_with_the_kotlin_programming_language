package com.tigerarcades.theater

import com.tigerarcades.theater.data.SeatRepository
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
    private lateinit var theaterService: TheaterService

    @Autowired
    private lateinit var seatRepository: SeatRepository

    @RequestMapping("")
    fun homepage(): ModelAndView = ModelAndView(
        "seatBooking",
        "bean",
        CheckAvailabilityBackingBean()
    )

    @RequestMapping(
        "/checkAvailability",
        method = [RequestMethod.POST]
    )
    fun checkAvailability(bean: CheckAvailabilityBackingBean): ModelAndView {
        val selectedSeat = theaterService.find(bean.selectedSeatNum, bean.selectedSeatRow)
        val result = bookingService.istSeatFree(selectedSeat)
        bean.result = "Seat $selectedSeat is ${if (result) "free" else "booked"}"
        return ModelAndView(
            "seatBooking",
            "bean",
            bean
        )
    }

    @RequestMapping(
        "performance"
    )
    fun performances():ModelAndView {
        return ModelAndView("performances")
    }

/*
    @RequestMapping("bootstrap")
    fun createInitialData(): ModelAndView {
        // create data and save to the database
        val seats = theaterService.seats
        seatRepository.saveAll(seats)
        return homepage()
    }
*/
}

class CheckAvailabilityBackingBean {
    val seatNums = 1..36
    val seatRows = 'A'..'O'
    var selectedSeatNum = 1
    var selectedSeatRow = 'A'
    var result = ""
}

