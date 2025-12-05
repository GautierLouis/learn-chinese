package com.louisgautier.apicontracts.dto

import kotlinx.serialization.Serializable

@Serializable
data class Decomposition(
    val symbolCode: Int,
    val glyphsCode: List<Int>
)