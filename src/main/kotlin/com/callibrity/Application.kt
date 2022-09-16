package com.callibrity

import com.callibrity.dao.DatabaseFactory
import io.ktor.server.application.*
import com.callibrity.plugins.*

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {
    DatabaseFactory.init()
    configureSerialization()
    configureMonitoring()
    configureRouting()
}
