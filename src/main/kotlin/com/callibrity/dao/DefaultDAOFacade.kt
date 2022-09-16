package com.callibrity.dao

import com.callibrity.dao.DatabaseFactory.dbQuery
import com.callibrity.models.Artist
import com.callibrity.models.Artists
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.*
import java.util.*

class DefaultDAOFacade : DAOFacade {
    private fun resultRowToArtist(row: ResultRow) = Artist(
        id = row[Artists.id],
        name = row[Artists.name],
        genre = row[Artists.genre],
    )

    override suspend fun allArtists(): List<Artist> = dbQuery {
        Artists.selectAll().map(::resultRowToArtist)
    }

    override suspend fun artist(id: String): Artist? = dbQuery {
        Artists
            .select { Artists.id eq id }
            .map(::resultRowToArtist)
            .singleOrNull()
    }

    override suspend fun addNewArtist(name: String, genre: String): Artist? = dbQuery {
        val insertStatement = Artists.insert {
            it[Artists.id] = UUID.randomUUID().toString()
            it[Artists.name] = name
            it[Artists.genre] = genre
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToArtist)
    }

    override suspend fun editArtist(id: String, name: String, genre: String): Artist? = dbQuery {
        Artists.update({ Artists.id eq id }) {
            it[Artists.name] = name
            it[Artists.genre] = genre
        }
        Artists
            .select { Artists.id eq id }
            .map(::resultRowToArtist)
            .singleOrNull()
    }

    override suspend fun deleteArtist(id: String): Boolean = dbQuery {
        Artists.deleteWhere { Artists.id eq id } > 0
    }
}

val dao: DAOFacade = DefaultDAOFacade().apply {
    runBlocking {
        if(allArtists().isEmpty()) {
            addNewArtist("Prince", "Rock")
            addNewArtist("deadmau5", "Electronic")
            addNewArtist("Taylor Swift", "Pop")
        }
    }
}
