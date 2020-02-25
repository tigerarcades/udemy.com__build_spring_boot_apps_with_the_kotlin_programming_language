package com.tigerarcades.theater.domain

import java.math.BigDecimal
import javax.persistence.*

@Entity
data class Seat(
    @Id @GeneratedValue(strategy = GenerationType.AUTO) @Column(name="id")val id: Long,
    @Column(name="seat_row") val row: Char,
    @Column(name="seat_num") val num: Int,
    @Column(name="price") val price: BigDecimal,
    @Column(name="description") val description: String
) {
    override fun toString() = "Seat ($row-$num $price $description)"
}
