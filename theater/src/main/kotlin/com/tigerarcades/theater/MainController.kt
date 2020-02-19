package com.tigerarcades.theater

import com.tigerarcades.theater.services.TheaterService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.servlet.ModelAndView

@Controller
class MainController {

    @Autowired
    private lateinit var theaterService: TheaterService

    @RequestMapping("")
    fun homepage(): ModelAndView = ModelAndView(
        "seatBooking.html",
        "bean",
        CheckAvailabilityBackingBean()
    )

    @RequestMapping(
        "/checkAvailability",
        method = [RequestMethod.POST]
    )
    fun checkAvailability(bean: CheckAvailabilityBackingBean): ModelAndView {
        return ModelAndView("seatBooking.html")
    }
}

class CheckAvailabilityBackingBean {
    val seatNums = 1..36
    val seatRows = 'A'..'O'
    var selectedSeatNum = 1
    var selectedSeatRow = 'A'
    var result = ""
}

