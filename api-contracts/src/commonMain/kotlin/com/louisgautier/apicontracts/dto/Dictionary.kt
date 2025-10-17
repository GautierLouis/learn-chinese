package com.louisgautier.apicontracts.dto

import kotlinx.serialization.Serializable

@Serializable
data class Dictionary(
    val character: Char,
    val definition: String = "",
    val pinyin: List<String> = emptyList(),
    val decomposition: String = "",
    val decompositionList: List<Decomposition> = emptyList(),
    val etymology: Etymology? = null,
    val radical: String? = null,
    val matches: List<List<Int>?> = emptyList()
)