package com.louisgautier.server.parser

class SVGGenerator() {

    fun generate(
        paths: List<String>,
        width: Int = 512,
        height: Int = 512,
    ): String {

        val formatted = paths.joinToString("\n") { p ->
            """<path d="$p"></path>"""
        }

        val medians = ""
//    = medians.map { mlist ->
//        mlist.map { p ->
//            """<path d="M ${mlist.joinToString(" L ") { "${it.first} ${it.second}" }}" fill="none" stroke="#e11" stroke-width="10" stroke-linecap="round" stroke-linejoin="round" stroke-dasharray="5,10"/>"""
//        }
//    }.flatten()


        return """<?xml version="1.0" encoding="UTF-8"?>
    <svg xmlns="http://www.w3.org/2000/svg" width="${width}px" height="${height}px" viewBox="0 0 1024 1024" preserveAspectRatio="xMidYMid meet">
      <g transform="scale(1, -1) translate(0, -900)"> 
        $formatted
      </g>
      <g transform="scale(1, -1) translate(0, -900)"> 
        $medians
      </g>
    </svg>
    """.trimIndent()
    }
}
