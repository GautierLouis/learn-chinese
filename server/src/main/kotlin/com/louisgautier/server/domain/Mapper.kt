package com.louisgautier.server.domain

import com.louisgautier.apicontracts.dto.Decomposition
import com.louisgautier.apicontracts.dto.Dictionary
import com.louisgautier.apicontracts.dto.DictionaryWithGraphic
import com.louisgautier.apicontracts.dto.Etymology
import com.louisgautier.apicontracts.dto.Graphic
import com.louisgautier.apicontracts.dto.SimpleDictionary
import com.louisgautier.server.database.entity.DictionaryTable
import com.louisgautier.server.database.entity.GraphicTable
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.sql.ResultRow


fun ResultRow.toGraphic() = Graphic(
    code = this[GraphicTable.code],
    strokes = this[GraphicTable.strokes].split(","),
    medians = Json.decodeFromString(this[GraphicTable.medians]),
)

fun ResultRow.toDictionary() = Dictionary(
    code = this[DictionaryTable.code],
    definition = this[DictionaryTable.definition].orEmpty(),
    pinyin = this[DictionaryTable.pinyin].orEmpty().split(","),
    decomposition = this[DictionaryTable.decomposition],
    decompositionList = Json.decodeFromString<List<Decomposition>>(this[DictionaryTable.decompositionList].orEmpty()),
    level = this[DictionaryTable.level],
    etymology = Etymology(
        type = this[DictionaryTable.etymologyType],
        phonetic = this[DictionaryTable.etymologyPhonetic],
        semantic = this[DictionaryTable.etymologySemantic],
        hint = this[DictionaryTable.etymologyHint],
    ),
    radical = this[DictionaryTable.radical],
    matches = Json.decodeFromString<List<List<Int>?>>(this[DictionaryTable.matches].orEmpty()),
)

fun ResultRow.toSimpleDictionary() = SimpleDictionary(
    code = this[DictionaryTable.code],
    pinyin = this[DictionaryTable.pinyin].orEmpty().split(","),
    level = this[DictionaryTable.level],
)

fun ResultRow.toDictionaryWithGraphic() = DictionaryWithGraphic(
    dictionary = this.toDictionary(),
    graphics = this.toGraphic()
)