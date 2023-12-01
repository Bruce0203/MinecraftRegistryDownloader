package io.github.bruce0203.scheme

import io.github.bruce0203.getAsJson
import io.ktor.client.*
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

suspend fun HttpClient.getManifest(version: Versions.Version) = getAsJson<Manifest>(version.url)
