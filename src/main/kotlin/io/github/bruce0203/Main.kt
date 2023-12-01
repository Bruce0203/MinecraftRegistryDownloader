package io.github.bruce0203

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.util.cio.*
import io.ktor.utils.io.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import java.io.File
import java.io.File.separator

fun main() = runBlocking<Unit> {
    val tempDirectory = File("_data")
    val serverJarFile = File(tempDirectory, "server.jar")
    val client = HttpClient(CIO)

    println("Downloading latest data generator of mojang...")
    client.downloadLatestServer(serverJarFile)

    println("Extracting data...")
    launchDataGenerator(serverJarFile, dir = tempDirectory)

    renameFile(File(tempDirectory, "generated${separator}reports"), File("output"))
    tempDirectory.deleteRecursively()

    println("Done!")
}

private fun renameFile(from: File, to: File) = from.renameTo(to)

private suspend fun HttpClient.downloadLatestServer(file: File) = downloadServer(
    getLatestManifest().downloads.server.url, file
)

private suspend fun HttpClient.downloadServer(url: String, file: File) {
    file.parentFile.mkdirs()
    get(url).bodyAsChannel().copyAndClose(file.writeChannel())
}

private suspend fun HttpClient.getLatestManifest() =
    getAsJson<Manifest>(getVersions().getLatestRelease().url)

private fun Versions.getLatestRelease() = versions.find { it.id == latest.release }!!

private suspend fun HttpClient.getVersions() = getAsJson<Versions>(
    "https://piston-meta.mojang.com/mc/game/version_manifest_v2.json"
)

private val json = Json { ignoreUnknownKeys = true }

private suspend inline fun <reified T> HttpClient.getAsJson(url: String) =
    json.decodeFromString<T>(getAsText(url))

private suspend fun HttpClient.getAsText(url: String) = get(url).bodyAsText()

private fun launchDataGenerator(jar: File, dir: File) {
    val cmd = "java -DbundlerMainClass=net.minecraft.data.Main -jar ${jar.name} --all"
    Runtime.getRuntime().exec(cmd, arrayOf(), dir).onExit().get()
}
