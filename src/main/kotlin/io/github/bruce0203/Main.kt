package io.github.bruce0203

import io.github.bruce0203.scheme.Versions
import io.github.bruce0203.scheme.getLatestRelease
import kotlinx.coroutines.runBlocking

fun main(args: Array<String>) = runBlocking<Unit> {
    when {
        args.contains("--releases") -> downloadAndGetRegistries {
            it.versions.filter { version -> version.type == Versions.VersionType.Release }
        }
        args.contains("--target") -> downloadAndGetRegistry { it.versions.find { it.id == args[1] }!! }
        args.contains("--all") -> downloadAndGetRegistries { it.versions }
        else -> downloadAndGetRegistry { it.getLatestRelease() }
    }
}
