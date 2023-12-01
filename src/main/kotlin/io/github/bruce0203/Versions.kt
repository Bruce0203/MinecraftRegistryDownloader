package io.github.bruce0203

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Versions(
    val latest: Latest,
    val versions: List<Version>
) {
    @Serializable
    data class Latest(val release: String, val snapshot: String)

    @Serializable
    enum class VersionType {
        @SerialName("snapshot")
        Snapshot,
        @SerialName("release")
        Release,
        @SerialName("old_beta")
        OldBeta,
        @SerialName("old_alpha")
        OldAlpha;
    }

    @Serializable
    data class Version(
        val id: String,
        val type: VersionType,
        val url: String,
        val time: String,
        val releaseTime: String,
        val sha1: String,
        val complianceLevel: Int
    )
}