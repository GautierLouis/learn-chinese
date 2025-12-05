package com.louisgautier.apicontracts.dto

import kotlinx.serialization.Serializable

@Serializable
data class DictionaryWithGraphic(
    val dictionary: Dictionary,
    val graphics: Graphic
) {
    val code: Int
        get() = dictionary.code
    val character: Char
        get() = Char(code)
}