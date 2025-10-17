package com.louisgautier.apicontracts.dto

import kotlinx.serialization.Serializable

@Serializable
data class Graphic(
    val character: Char,
    val strokes: List<String>,
    val medians: List<List<List<Double>>>
)