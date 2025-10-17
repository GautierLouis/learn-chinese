package com.louisgautier.sample.server.parser

sealed class IdeographicNode {
    data class Operator(val op: IdeographicChar, val children: List<IdeographicNode>) : IdeographicNode()
    data class Glyph(val char: Char) : IdeographicNode()
}