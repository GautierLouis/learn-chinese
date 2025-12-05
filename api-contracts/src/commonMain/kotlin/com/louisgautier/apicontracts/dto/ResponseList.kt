package com.louisgautier.apicontracts.dto

import kotlinx.serialization.Serializable

@Serializable
data class ResponseList<T>(
    val hasNextPage: Boolean,
    val data: List<T>,
)