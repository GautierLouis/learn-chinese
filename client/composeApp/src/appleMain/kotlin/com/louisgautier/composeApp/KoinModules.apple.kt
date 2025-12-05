package com.louisgautier.composeApp

import com.louisgautier.utils.AppConfig
import com.louisgautier.utils.context.ContextWrapper
import platform.Foundation.NSBundle

actual fun buildPlatformFlavor(contextWrapper: ContextWrapper): AppConfig {
    return AppConfig(
        platform = "iOS",
        flavor = "prod",
        isProduction = true,
        versionName = NSBundle.mainBundle.infoDictionary?.get("CFBundleShortVersionString") as? String ?: "unknow",
        versionCode = NSBundle.mainBundle.infoDictionary?.get("CFBundleShortVersionString") as? String ?: "unknow"
    )
}