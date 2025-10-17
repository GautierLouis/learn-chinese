package com.louisgautier.apicontracts.dto

import kotlinx.serialization.Serializable

@Serializable
data class Decomposition(
    val symbol: Char,
    val glyphs: List<Char>
)