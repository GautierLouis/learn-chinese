package com.louisgautier.sample.server

import com.louisgautier.apicontracts.dto.AuthUserJson
import com.louisgautier.apicontracts.dto.ResponseError
import com.louisgautier.apicontracts.dto.ResponseList
import com.louisgautier.apicontracts.dto.UserRefreshTokenJson
import com.louisgautier.apicontracts.routing.EndPoint
import com.louisgautier.sample.server.domain.AuthenticationRepository
import com.louisgautier.sample.server.domain.DictionaryRepository
import com.louisgautier.sample.server.domain.GraphicRepository
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.principal
import io.ktor.server.plugins.openapi.openAPI
import io.ktor.server.plugins.swagger.swaggerUI
import io.ktor.server.request.receive
import io.ktor.server.resources.get
import io.ktor.server.resources.post
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import io.micrometer.prometheusmetrics.PrometheusMeterRegistry
import org.koin.ktor.ext.inject

fun Application.configureRouting() {

    routing {
        configureAdminRoutes()
        configureOpenRoutes()
        configureAuthedRoutes()
        configureCharacterRoute()

        get("/") {
            call.respondText("Ktor")
        }
    }
}

private fun Routing.configureAuthedRoutes() {
    authenticate(Constants.JWT_NAME) {
        val authenticationRepository: AuthenticationRepository by inject()

        get<EndPoint.Me> {
            val principal = call.principal<JWTPrincipal>()!!
            val supabaseUserId = principal.payload.getClaim("sub").asString()
            call.respond(mapOf("user_id" to supabaseUserId))
        }

        post<EndPoint.RefreshToken> {
            val creds = call.receive<UserRefreshTokenJson>()
            authenticationRepository.refreshSession(creds.refreshToken).collect {
                it.onSuccess { data ->
                    call.respond(HttpStatusCode.OK, data)
                }
                it.onFailure {
                    call.respond(HttpStatusCode.InternalServerError)
                }
            }
        }
    }
}

private fun Routing.configureOpenRoutes() {
    val authenticationRepository: AuthenticationRepository by inject()

    get<EndPoint.RegisterAnonymously> {
        authenticationRepository.registerAnonymously().collect {
            it.onSuccess { data ->
                call.respond(HttpStatusCode.OK, data)
            }
            it.onFailure {
                call.respond(HttpStatusCode.InternalServerError)
            }
        }
    }

    post<EndPoint.Register> {
        val creds = call.receive<AuthUserJson>()

        authenticationRepository.registerWith(creds.email, creds.password).collect {
            it.onSuccess {
                call.respond(HttpStatusCode.OK, it)
            }
            it.onFailure {
                call.respond(HttpStatusCode.InternalServerError)
            }
        }
    }

    post<EndPoint.Login> {
        val creds = call.receive<AuthUserJson>()

        authenticationRepository.loginInWith(creds.email, creds.password).collect {
            it.onSuccess {
                call.respond(HttpStatusCode.OK, it)
            }
            it.onFailure {
                call.respond(HttpStatusCode.InternalServerError)
            }
        }
    }
}

private fun Routing.configureAdminRoutes() {
    val appMicrometerRegistry: PrometheusMeterRegistry by inject()

    swaggerUI(path = "admin/swagger")
    openAPI(path = "admin/openapi")
    get<EndPoint.Admin.Metrics> {
        call.respond(appMicrometerRegistry.scrape())
    }
}

private fun Routing.configureCharacterRoute() {
    val dictionaryRepository: DictionaryRepository by inject()
    val graphicRepository: GraphicRepository by inject()

    get<EndPoint.Characters> {
        val page = call.request.queryParameters["page"]?.toInt() ?: 0
        val limit = call.request.queryParameters["limit"]?.toInt() ?: 100
        dictionaryRepository.getAll(page, limit).let {
            call.respond(HttpStatusCode.OK, ResponseList(page, limit, it))
        }
    }

    get<EndPoint.Characters.ByName> { resource ->

        val result = dictionaryRepository.get(resource.character)

        if (result == null) {
            call.respond(
                HttpStatusCode.NotFound,
                ResponseError("Glyph not found: ${resource.character}")
            )
        }

        call.respond(HttpStatusCode.OK, result!!)
    }

    get<EndPoint.Characters.ByName.SVG> { resource ->

        val result = graphicRepository.get(resource.parent.character)

        if (result == null) {
            call.respond(
                HttpStatusCode.NotFound,
                ResponseError("SVG not found: ${resource.parent.character}")
            )
        }

        call.respond(HttpStatusCode.OK, result!!)
    }
}