package com.louisgautier.sample.server.database.entity

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object GraphicTable : IntIdTable("graphic") {
    val character = char("character").uniqueIndex()
    val strokes = text("strokes")
    val medians = text("medians")

}

class GraphicDao(id: EntityID<Int>) : IntEntity(id) {
    companion object Companion : IntEntityClass<GraphicDao>(GraphicTable)

    val character by GraphicTable.character
    val strokes by GraphicTable.strokes
    val medians by GraphicTable.medians
}
