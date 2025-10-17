package com.louisgautier.sample.server.database.entity

import com.louisgautier.apicontracts.dto.EtymologyType
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable


object DictionaryTable : IntIdTable("dictionary") {
    val character = char("character").uniqueIndex()
    val definition = text("definition").nullable()
    val pinyin = text("pinyin").nullable()
    val decomposition = varchar("original_decomposition", 110)
    val decompositionList = text("decomposition").nullable()
    val etymologyType = enumerationByName("etymology_type", 50, EtymologyType::class).nullable()
    val etymologyPhonetic = text("etymology_phonetic").nullable()
    val etymologySemantic = text("etymology_semantic").nullable()
    val etymologyHint = text("etymology_hint").nullable()
    val radical = varchar("radical", 4).nullable()
    val matches = text("matches").nullable()
}

class DictionaryDao(id: EntityID<Int>) : IntEntity(id) {
    companion object Companion : IntEntityClass<DictionaryDao>(DictionaryTable)

    val character by DictionaryTable.character
    val definition by DictionaryTable.definition
    val pinyin by DictionaryTable.pinyin
    val originalDecomposition by DictionaryTable.decomposition
    val decomposition by DictionaryTable.decompositionList
    val etymologyType by DictionaryTable.etymologyType
    val etymologyPhonetic by DictionaryTable.etymologyPhonetic
    val etymologySemantic by DictionaryTable.etymologySemantic
    val etymologyHint by DictionaryTable.etymologyHint
    val radical by DictionaryTable.radical
    val matches by DictionaryTable.matches
}



