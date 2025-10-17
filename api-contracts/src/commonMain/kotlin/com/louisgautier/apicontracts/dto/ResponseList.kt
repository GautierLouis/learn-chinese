package com.louisgautier.apicontracts.dto

import kotlinx.serialization.Serializable

@Serializable
data class ResponseList<T>(
    val page: Int,
    val count: Int,
    val data: List<T>,
)