package com.louisgautier.network

import com.louisgautier.network.NamedNetworkModule.AUTHED_CLIENT
import com.louisgautier.network.NamedNetworkModule.BASE_URL
import com.louisgautier.network.NamedNetworkModule.UNAUTHED_CLIENT
import com.louisgautier.network.interfaces.AuthService
import com.louisgautier.network.interfaces.CharacterService
import com.louisgautier.network.interfaces.UserService
import com.louisgautier.utils.AppConfig
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

private object NamedNetworkModule {
    const val UNAUTHED_CLIENT = "UNAUTHED_CLIENT"
    const val AUTHED_CLIENT = "AUTHED_CLIENT"
    const val BASE_URL = "BASE_URL"
}

val networkModule: Module = module {
    single(named(BASE_URL)) {
        when (get<AppConfig>().flavor) {
            "dev" -> "10.0.2.2"
            "staging" -> "https://preprod.url"
            else -> "https://prod.url"
        }
    }

    single {
        DefaultService(
            tokenAccessor = get(),
            baseUrl = get(named(BASE_URL)),
            appConfig = get()
        )
    }

    single(named(UNAUTHED_CLIENT)) { get<DefaultService>().unauthedClient }
    single(named(AUTHED_CLIENT)) { get<DefaultService>().authedClient }

    single { DefaultAuthService(get(named(UNAUTHED_CLIENT))) } bind AuthService::class
    single { DefaultUserService(get(named(AUTHED_CLIENT))) } bind UserService::class
    single { DefaultCharacterService(get(named(UNAUTHED_CLIENT))) } bind CharacterService::class
}
