package com.louisgautier.sample.server

import com.louisgautier.sample.server.database.configureDatabase
import com.louisgautier.sample.server.parser.FileParser
import io.ktor.server.application.Application
import io.ktor.server.netty.EngineMain
import kotlinx.coroutines.launch
import org.koin.core.module.Module
import org.koin.ktor.ext.inject

/**
 * Main entry point for the application
 * Refer to application.conf for configuration
 */
fun main(args: Array<String>): Unit = EngineMain.main(args)

fun Application.module(
    envModule: Module = buildEnvModule(this.environment)
) {

    installModule(envModule)
    configureDatabase()
    configureServer()
    configureRouting()

    prefillDatabase()
}

private fun Application.prefillDatabase() {
    val fileParser: FileParser by inject()

    launch {
        fileParser.init()
    }
}
