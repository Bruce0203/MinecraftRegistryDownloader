package io.github.bruce0203

import kotlinx.serialization.Serializable

@Serializable
data class Manifest(
    val downloads: Downloads
) {
    @Serializable
    data class Downloads(
        val server: Server
    ) {
        @Serializable
        data class Server(
            val sha1: String,
            val size: Long,
            val url: String
        )
    }
}