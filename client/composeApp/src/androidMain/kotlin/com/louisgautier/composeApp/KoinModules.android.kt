package com.louisgautier.composeApp

import com.louisgautier.utils.AppConfig
import com.louisgautier.utils.context.ContextWrapper

actual fun buildPlatformFlavor(contextWrapper: ContextWrapper): AppConfig {
    return AppConfig(
        platform = "Android",
        flavor = BuildConfig.FLAVOR,
        isProduction = BuildConfig.FLAVOR == "prod",
        versionName = BuildConfig.VERSION_NAME,
        versionCode = BuildConfig.VERSION_CODE.toString()
    )
}