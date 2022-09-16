package com.callibrity.dao

import com.callibrity.models.*
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.config.*
import kotlinx.coroutines.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.*
import org.jetbrains.exposed.sql.transactions.experimental.*

object DatabaseFactory {
    fun init(config: ApplicationConfig) {
        val port = config.property("port").getString()
        val host = config.property("host").getString()
        val name = config.property("name").getString()
        val username = config.property("user").getString()
        val password = config.property("pass").getString()
        val jdbcUrl = "jdbc:postgresql://$host:$port/$name"

        val hikariConfig = HikariConfig().apply {
            this.jdbcUrl = jdbcUrl
            this.username = username
            this.password = password
        }
        val dataSource = HikariDataSource(hikariConfig)
        val database = Database.connect(dataSource)

        transaction(database) {
            SchemaUtils.create(Artists)
        }
    }

    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }
}