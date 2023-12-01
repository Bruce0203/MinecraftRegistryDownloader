package io.github.bruce0203

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.serialization.json.Json

val json = Json { ignoreUnknownKeys = true }

suspend inline fun <reified T> HttpClient.getAsJson(url: String) =
    json.decodeFromString<T>(getAsText(url))

suspend fun HttpClient.getAsText(url: String) = get(url).bodyAsText()
