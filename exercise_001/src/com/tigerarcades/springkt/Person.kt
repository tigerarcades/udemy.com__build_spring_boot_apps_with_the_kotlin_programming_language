package com.tigerarcades.springkt

import java.util.*

class Person(
    private val id: Long,
    private val title: String,
    private val firstName: String,
    private val surname: String,
    private val dateOfBirth: Calendar?
) {

    val age: Int
        get() = when (dateOfBirth != null) {
            true -> age(dateOfBirth)
            false -> -1
        }

    companion object {
        fun age(dateOfBirth: Calendar): Int {
            val today = GregorianCalendar()
            val years = today.get(Calendar.YEAR) - dateOfBirth.get(Calendar.YEAR)
            if (dateOfBirth.get(Calendar.DAY_OF_YEAR) >= today.get(Calendar.YEAR)) {
                return years - 1
            }
            return years
        }
    }

    override fun toString() = "$title $firstName $surname"
}

fun main() {
    val john = Person(1L, "Mr", "John", "Blue", GregorianCalendar(1977, 9, 3))
    val jane = Person(2L, "Mrs", "Jane", "Green", null)
    println("$john's age is ${john.age}")
    println("$jane's age is ${jane.age}")
    println(
        "The age of someone born on 3rd May 1988 is ${Person.age(GregorianCalendar(1988, 5, 3))}"
    )
    println("The older person was ${if (john.age > jane.age) john else jane}")
}