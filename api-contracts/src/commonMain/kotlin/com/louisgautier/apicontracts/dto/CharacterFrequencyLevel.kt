package com.louisgautier.apicontracts.dto

import com.louisgautier.apicontracts.KeepForR8
import kotlinx.serialization.Serializable

@KeepForR8
@Serializable
enum class CharacterFrequencyLevel {
    UNKNOWN,
    COMMON,
    FREQUENT,
    STANDARD,
    EXTENDED,
    RARE,
    OBSOLETE;

    companion object {
        val validEntry = listOf(COMMON, FREQUENT, STANDARD)
    }
}

