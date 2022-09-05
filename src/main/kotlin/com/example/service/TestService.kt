package com.example.service

import org.springframework.stereotype.Service
import java.time.Clock
import java.time.ZonedDateTime

@Service
class TestService(
    private val clock: Clock
) {
    fun printZonedDatetime(): String {
        val currentDateTime = ZonedDateTime.now()
        return currentDateTime.toString()
    }

    fun printZonedDatetimeWithClock(): String {
        val currentDateTime = ZonedDateTime.now(clock)
        return currentDateTime.toString()
    }
}