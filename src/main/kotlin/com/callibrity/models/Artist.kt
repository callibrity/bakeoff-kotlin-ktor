package com.callibrity.models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table
import java.util.*

@Serializable
data class Artist(val id: String = UUID.randomUUID().toString(), val name: String, val genre: String)

object Artists : Table() {
    val id = varchar("id", 255)
    val name = varchar("name", 255)
    val genre = varchar("genre", 255)

    override val primaryKey = PrimaryKey(id)
}

val artistStorage = mutableListOf<Artist>()