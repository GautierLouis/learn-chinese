package com.louisgautier.apicontracts.dto

import com.louisgautier.apicontracts.KeepForR8
import kotlinx.serialization.SerialName

@KeepForR8
enum class EtymologyType {
    @SerialName("ideographic")
    IDEOGRAPHIC,

    @SerialName("pictographic")
    PICTOGRAPHIC,

    @SerialName("pictophonetic")
    PICTOPHONETIC,
}