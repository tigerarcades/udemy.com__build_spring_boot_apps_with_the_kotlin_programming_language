package com.tigerarcades.theater.domain

import java.math.BigDecimal

data class Seat(
    val row: Char,
    val num: Int,
    val price: BigDecimal,
    val description: String
) {
    override fun toString() = "Seat ($row-$num $price $description)"
}
