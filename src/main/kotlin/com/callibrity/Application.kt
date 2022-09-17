package com.callibrity

import com.callibrity.dao.DatabaseFactory
import io.ktor.server.application.*
import com.callibrity.plugins.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*

fun main() {
    embeddedServer(CIO, port = 8080, host = "0.0.0.0") {
        configureRouting()
    }.start(wait = true)
}

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {
    val databaseConfig = environment.config.config("ktor.database")
    DatabaseFactory.init(databaseConfig)
    configureSerialization()
    configureMonitoring()
    configureRouting()
}
