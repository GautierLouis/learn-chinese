package com.louisgautier.apicontracts.dto

import kotlinx.serialization.Serializable

@Serializable
data class Etymology(
    val type: EtymologyType? = null,
    val phonetic: String? = null,
    val semantic: String? = null,
    val hint: String? = null,
)