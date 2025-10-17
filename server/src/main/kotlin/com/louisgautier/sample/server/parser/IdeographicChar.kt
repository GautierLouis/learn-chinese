package com.louisgautier.sample.server.parser

enum class IdeographicChar(val symbol: Char, val codepoint: Int, val arity: Int) {
    LEFT_TO_RIGHT('⿰', 0x2FF0, 2),
    ABOVE_TO_BELOW('⿱', 0x2FF1, 2),
    LEFT_TO_MIDDLE_AND_RIGHT('⿲', 0x2FF2, 3),
    ABOVE_TO_MIDDLE_AND_BELOW('⿳', 0x2FF3, 3),
    SURROUND('⿴', 0x2FF4, 2),
    SURROUND_FROM_ABOVE('⿵', 0x2FF5, 2),
    SURROUND_FROM_BELOW('⿶', 0x2FF6, 2),
    SURROUND_FROM_LEFT('⿷', 0x2FF7, 2),
    SURROUND_FROM_UPPER_LEFT('⿸', 0x2FF8, 2),
    SURROUND_FROM_UPPER_RIGHT('⿹', 0x2FF9, 2),
    SURROUND_FROM_LOWER_LEFT('⿺', 0x2FFA, 2),
    OVERLAID('⿻', 0x2FFB, 2);

    companion object Companion {
        private val byCode = entries.associateBy { it.codepoint }

        fun fromCodepoint(cp: Int): IdeographicChar? = byCode[cp]
    }
}
