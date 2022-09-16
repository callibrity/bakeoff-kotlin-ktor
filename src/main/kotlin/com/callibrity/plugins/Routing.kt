package com.callibrity.plugins

import com.callibrity.routes.artistRouting
import io.ktor.server.routing.*
import io.ktor.server.application.*

fun Application.configureRouting() {
    install(IgnoreTrailingSlash)
    routing {
        route("/api") {
            artistRouting()
        }
    }
}
