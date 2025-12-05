package com.louisgautier.firebase

import org.koin.dsl.bind
import org.koin.dsl.module

actual val firebasePlatformModule = module {
    single { AndroidFirebaseManager(get(), get()) } bind FirebaseManager::class
}