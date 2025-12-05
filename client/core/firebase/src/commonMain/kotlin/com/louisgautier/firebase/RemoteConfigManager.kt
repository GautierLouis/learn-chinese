package com.louisgautier.firebase

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow

class RemoteConfigManager {
    private val _events = MutableStateFlow(RemoteConfigFlags())
    val events = _events.asSharedFlow() // Map<Key, Any> will be better

    fun register(flags: RemoteConfigFlags) {
        _events.tryEmit(flags)
    }
}