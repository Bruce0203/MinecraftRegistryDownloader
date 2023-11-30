import io.github.bruce0203.Manifest
import io.github.bruce0203.Versions
import io.kotest.core.spec.style.StringSpec
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.util.cio.*
import io.ktor.utils.io.*
import java.io.File


val Json = kotlinx.serialization.json.Json {
    ignoreUnknownKeys = true
}

class Test1 : StringSpec({
    "load version manifest" {
        val client = HttpClient(CIO)
        val body = client.get("https://piston-meta.mojang.com/mc/game/version_manifest_v2.json").bodyAsText()
        val manifest = Json.decodeFromString<Versions>(body).run {
            val latest = versions.find { it.id == latest.release }!!
            Json.decodeFromString<Manifest>(
                client.get(latest.url).bodyAsText()
            )
        }
        val file = File(File("_data").apply { mkdirs() }, "server.jar")
        client.get(
            manifest.downloads.server.url
        ).bodyAsChannel().copyAndClose(file.writeChannel())
        val tempDirectory = file.parentFile
        launchJar(file.name, tempDirectory)
        File(tempDirectory, "generated${File.separator}reports").renameTo(File("output"))
        tempDirectory.deleteRecursively()
    }
})

private fun launchJar(jar: String, dir: File) {
    val cmd = "java -DbundlerMainClass=net.minecraft.data.Main -jar $jar --all"
    Runtime.getRuntime().exec(cmd, arrayOf(), dir).onExit().get()
}
