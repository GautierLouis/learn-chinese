package com.louisgautier.firebase

import com.louisgautier.firebase.notification.AndroidPushNotificationManager
import com.louisgautier.firebase.notification.PushNotificationManager
import org.koin.dsl.bind
import org.koin.dsl.module

actual val firebasePlatformModule = module {
    single { AndroidPushNotificationManager(get()) } bind PushNotificationManager::class
    single { FirebaseManager(get(), get()) }

}