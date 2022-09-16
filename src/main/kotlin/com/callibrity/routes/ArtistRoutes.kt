package com.callibrity.routes

import com.callibrity.dao.dao
import com.callibrity.models.CreateArtistRequest
import com.callibrity.models.UpdateArtistRequest
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.artistRouting() {
    route("/artists") {
        get {
            call.respond(dao.allArtists())
        }
        get("{id?}") {
            val id = call.parameters["id"] ?: return@get call.respondText(
                "Missing id",
                status = HttpStatusCode.BadRequest
            )
            val artist = dao.artist(id) ?: return@get call.respondText("Not found", status = HttpStatusCode.NotFound)
            call.respond(artist)
        }
        put("{id?}") {
            val id = call.parameters["id"] ?: return@put call.respondText(
                "Missing id",
                status = HttpStatusCode.BadRequest
            )
            val updateArtistRequest = call.receive<UpdateArtistRequest>()
            val artist = dao.editArtist(id, updateArtistRequest.name, updateArtistRequest.genre)
                ?: return@put call.respond(status = HttpStatusCode.NotFound, "Not found")

            call.respond(artist)
        }
        post {
            val artist = call.receive<CreateArtistRequest>()
            val createdArtist = dao.addNewArtist(artist.name, artist.genre) ?:
                return@post call.respond(status = HttpStatusCode.InternalServerError, "Failed to create artist")

            call.respond(createdArtist)
        }
        delete("{id?}") {
            val id = call.parameters["id"] ?: return@delete call.respondText(
                "Missing id",
                status = HttpStatusCode.BadRequest
            )

            if (dao.deleteArtist(id))
                call.respond(status = HttpStatusCode.OK, "OK")
            else
                call.respond(status = HttpStatusCode.NotFound, "Not found")
        }
    }
}