package com.louisgautier.composeApp

import com.louisgautier.utils.AppConfig
import com.louisgautier.utils.context.ContextWrapper

actual fun buildPlatformFlavor(contextWrapper: ContextWrapper): AppConfig {
    return AppConfig(
        platform = "Desktop",
        flavor = "prod",
        isProduction = true,
        versionName = "1.0.0",
        versionCode = "1.0.0"
    )
}