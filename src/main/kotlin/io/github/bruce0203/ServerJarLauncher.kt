package io.github.bruce0203

import io.github.bruce0203.scheme.Manifest
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.util.cio.*
import io.ktor.utils.io.*
import java.io.File

fun launchDataGenerator(jar: File, dir: File) {
    val cmd = "java -DbundlerMainClass=net.minecraft.data.Main -jar ${jar.name} --all"
    Runtime.getRuntime().exec(cmd, arrayOf(), dir).onExit().get()
}

suspend fun HttpClient.downloadLatestServer(manifest: Manifest, file: File) =
    downloadServer(manifest.downloads.server.url, file)

suspend fun HttpClient.downloadServer(url: String, file: File) {
    file.parentFile.mkdirs()
    get(url).bodyAsChannel().copyAndClose(file.writeChannel())
}

fun renameFile(from: File, to: File) = from.renameTo(to.apply { mkdirs() })
