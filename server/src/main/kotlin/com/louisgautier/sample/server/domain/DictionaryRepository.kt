package com.louisgautier.sample.server.domain

import com.louisgautier.sample.server.database.entity.DictionaryTable
import com.louisgautier.sample.server.database.entity.DictionaryTable.character
import com.louisgautier.sample.server.database.entity.DictionaryTable.decomposition
import com.louisgautier.sample.server.database.entity.DictionaryTable.decompositionList
import com.louisgautier.sample.server.database.entity.DictionaryTable.definition
import com.louisgautier.sample.server.database.entity.DictionaryTable.etymologyHint
import com.louisgautier.sample.server.database.entity.DictionaryTable.etymologyPhonetic
import com.louisgautier.sample.server.database.entity.DictionaryTable.etymologySemantic
import com.louisgautier.sample.server.database.entity.DictionaryTable.etymologyType
import com.louisgautier.sample.server.database.entity.DictionaryTable.pinyin
import com.louisgautier.sample.server.database.entity.DictionaryTable.radical
import com.louisgautier.sample.server.database.entity.DictionaryTable.matches
import com.louisgautier.apicontracts.dto.Decomposition
import com.louisgautier.apicontracts.dto.Dictionary
import com.louisgautier.apicontracts.dto.Etymology
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.selectAll

class DictionaryRepository {

    fun ResultRow.toDictionary() = Dictionary(
        character = this[character],
        definition = this[definition].orEmpty(),
        pinyin = this[pinyin].orEmpty().split(","),
        decomposition = this[decomposition],
        decompositionList = Json.decodeFromString<List<Decomposition>>(this[decompositionList].orEmpty()),
        etymology = Etymology(
            type = this[etymologyType],
            phonetic = this[etymologyPhonetic],
            semantic = this[etymologySemantic],
            hint = this[etymologyHint],
        ),
        radical = this[radical],
        matches = Json.decodeFromString<List<List<Int>?>>(this[matches].orEmpty()),
    )

    suspend fun getAll(page: Int, limit: Int): List<Dictionary> = suspendTransaction {
        DictionaryTable.selectAll()
            .limit(limit)
            .offset(page * limit.toLong())
            .map { it.toDictionary() }
    }

    suspend fun get(character: Char): Dictionary? = suspendTransaction {
        DictionaryTable.selectAll().where { DictionaryTable.character eq character }.limit(1)
            .map { it.toDictionary() }.firstOrNull()
    }

    suspend fun batchCreate(dictionary: List<Dictionary>) = suspendTransaction {
        DictionaryTable.batchInsert(
            dictionary,
            ignore = true,
            shouldReturnGeneratedValues = false
        ) { dictionary ->
            this[character] = dictionary.character
            this[definition] = dictionary.definition
            this[pinyin] = dictionary.pinyin.joinToString()
            this[decomposition] = dictionary.decomposition
            this[decompositionList] = Json.encodeToString(dictionary.decompositionList)
            this[etymologyType] = dictionary.etymology?.type
            this[etymologyPhonetic] = dictionary.etymology?.phonetic
            this[etymologySemantic] = dictionary.etymology?.semantic
            this[etymologyHint] = dictionary.etymology?.hint
            this[radical] = dictionary.radical
            this[matches] = Json.encodeToString(dictionary.matches)
        }
    }

}