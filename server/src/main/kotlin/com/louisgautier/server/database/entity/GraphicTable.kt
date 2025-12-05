package com.louisgautier.server.database.entity

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object GraphicTable : IntIdTable("graphic") {
    val code = integer("code").uniqueIndex()
    val strokes = text("strokes")
    val medians = text("medians")

}

class GraphicDao(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<GraphicDao>(GraphicTable)

    val strokes by GraphicTable.strokes
    val medians by GraphicTable.medians
}
