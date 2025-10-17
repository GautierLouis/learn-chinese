package com.louisgautier.apicontracts.dto

import kotlinx.serialization.Serializable

@Serializable
data class ResponseError(
    val message: String,
    val code: Int = 0,
    val additionalInfo: String? = null,
)