package com.louisgautier.firebase

import kotlinx.cinterop.ExperimentalForeignApi
import org.koin.dsl.module

@OptIn(ExperimentalForeignApi::class)
actual val firebasePlatformModule = module {
    single { FirebaseManager().also { it.initialize() } }
}