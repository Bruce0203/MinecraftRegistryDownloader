package io.github.bruce0203

import io.github.bruce0203.scheme.Versions
import io.github.bruce0203.scheme.getManifest
import io.github.bruce0203.scheme.getVersions
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import java.io.File
import java.io.File.separator

suspend fun downloadAndGetRegistry(finder: suspend (Versions) -> Versions.Version) =
    downloadAndGetRegistries { listOf(finder(it)) }.first()

suspend fun downloadAndGetRegistries(finder: suspend (Versions) -> List<Versions.Version>): List<File> {
    val client = HttpClient(CIO)
    return finder(client.getVersions()).map { version ->
        client.downloadAndGetRegistry(version)
    }
}

suspend fun HttpClient.downloadAndGetRegistry(version: Versions.Version): File {
    val tempDirectory = File("_data")
    val serverJarFile = File(tempDirectory, "server.jar")
    val manifest = getManifest(version)
    println("Downloading ${version.id} data generator of mojang...")
    downloadLatestServer(manifest, serverJarFile)

    println("Extracting ${version.id} data...")
    launchDataGenerator(serverJarFile, dir = tempDirectory)

    val output = File("output${separator}v${version.id}")
    renameFile(File(tempDirectory, "generated${separator}reports"), output)
    tempDirectory.deleteRecursively()

    println("Done: ${output.name}")
    return output
}