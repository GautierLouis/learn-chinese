package com.louisgautier.firebase

import com.louisgautier.firebase.notification.PushNotificationManager
import kotlinx.cinterop.ExperimentalForeignApi
import org.koin.dsl.bind
import org.koin.dsl.module

@OptIn(ExperimentalForeignApi::class)
actual val firebasePlatformModule = module {
    single { PushNotificationManager() }
    single { iOSFirebaseManager() } bind FirebaseManager::class
}