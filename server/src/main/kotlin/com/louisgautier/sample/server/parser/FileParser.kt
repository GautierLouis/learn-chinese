package com.louisgautier.sample.server.parser

import com.louisgautier.sample.server.database.entity.DictionaryDao
import com.louisgautier.sample.server.database.entity.GraphicDao
import com.louisgautier.sample.server.domain.DictionaryRepository
import com.louisgautier.sample.server.domain.GraphicRepository
import com.louisgautier.apicontracts.dto.Decomposition
import com.louisgautier.apicontracts.dto.Dictionary
import com.louisgautier.apicontracts.dto.Graphic
import com.louisgautier.sample.server.domain.suspendTransaction
import kotlinx.serialization.json.Json

class FileParser(
    private val dictionaryRepository: DictionaryRepository,
    private val graphicRepository: GraphicRepository,
) {
    companion object {
        private const val DICTIONARY_FILE: String = "dictionary.txt"
        private const val GRAPHIC_FILE: String = "graphics.txt"
        private const val EMPTY_COUNT = 0L
    }

    suspend fun init() {
        try {
            val (dictionaryEntryCount, graphicCount) = getDatabaseCounts()

            if (dictionaryEntryCount == EMPTY_COUNT) {
                val dict = parseDictionary().map {
                    it.copy(
                        decompositionList = decompose(it.decomposition)
                    )
                }
                dictionaryRepository.batchCreate(dict)
            }
            if (graphicCount == EMPTY_COUNT) {
                val graph = parseGraphic()
                graphicRepository.batchCreate(graph)
            }
        } catch (e: Exception) {
            println("An error occurred during data initialization: ${e.message}")
        }
    }

    private suspend fun getDatabaseCounts(): Pair<Long, Long> = suspendTransaction {
        Pair(DictionaryDao.count(), GraphicDao.count())
    }

    private fun parseDictionary(): List<Dictionary> {
        return parseNdjsonLines(path = DICTIONARY_FILE)
    }

    private fun parseGraphic(): List<Graphic> {
        return parseNdjsonLines(path = GRAPHIC_FILE)
    }

    private fun decompose(original: String): List<Decomposition> {
        return try {
            val parser = IdeographicParser(original)
            val tree = parser.parse()
            decompositionOrderedJsonFromIds(tree)
        } catch (_: Throwable) {
            emptyList()
        }
    }

    private fun decompositionOrderedJsonFromIds(
        node: IdeographicNode,
    ): List<Decomposition> {
        val decompositionMap = linkedMapOf<Char, MutableList<Char>>()

        fun walk(n: IdeographicNode) {
            when (n) {
                is IdeographicNode.Glyph -> { /* nothing to emit at glyph nodes */
                }

                is IdeographicNode.Operator -> {
                    // For children, if child is Glyph -> child char, if Operator -> operator symbol
                    val childrenStrings = n.children.map { child ->
                        when (child) {
                            is IdeographicNode.Glyph -> child.char
                            is IdeographicNode.Operator -> child.op.symbol
                        }
                    }

                    val list = decompositionMap.getOrPut(n.op.symbol) { mutableListOf() }
                    list.addAll(childrenStrings)

                    // then recurse into children so later operators are processed in preorder
                    n.children.forEach { walk(it) }
                }
            }
        }

        walk(node)

        return decompositionMap.map { Decomposition(it.key, it.value) }
    }

    private fun serializeOriginal(node: IdeographicNode): String {
        return when (node) {
            is IdeographicNode.Glyph -> node.char.toString()
            is IdeographicNode.Operator -> node.op.symbol + node.children.joinToString(separator = "") {
                serializeOriginal(it)
            }
        }
    }

    private inline fun <reified T> parseNdjsonLines(
        path: String,
        json: Json = Json { ignoreUnknownKeys = false }
    ): List<T> {
        val stream = object {}.javaClass.classLoader.getResourceAsStream(path)
            ?: throw IllegalArgumentException("Resource not found: $path")
        val text = stream.bufferedReader(Charsets.UTF_8).use { it.readText() }
        return text.lineSequence()
            .map { it.trim() }
            .filter { it.isNotEmpty() }
            .map { json.decodeFromString<T>(it) }
            .toList()
    }
}

