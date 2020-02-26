package com.tigerarcades.theater.domain

import javax.persistence.*

@Entity
data class Performance(
    @Id @GeneratedValue(strategy = GenerationType.AUTO) val id: Long,
    val name: String
)