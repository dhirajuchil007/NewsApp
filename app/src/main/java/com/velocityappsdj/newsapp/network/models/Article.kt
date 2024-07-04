package com.velocityappsdj.newsapp.network.models


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import androidx.navigation.NavType
import kotlinx.serialization.Serializable
import java.time.Duration
import java.time.Instant
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@Parcelize
@Serializable
data class Article(
    @SerializedName("author")
    val author: String? = null,
    @SerializedName("content")
    val content: String? = null,
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("publishedAt")
    val publishedAt: String,
    @SerializedName("source")
    val source: Source? = null,
    @SerializedName("title")
    val title: String,
    @SerializedName("url")
    val url: String? = null,
    @SerializedName("urlToImage")
    val urlToImage: String? = null
) : Parcelable {
    fun getTimeAgo(): String {
        val dateFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME
        val timestamp =
            OffsetDateTime.parse(publishedAt, dateFormatter).toInstant()
        val now = Instant.now().atOffset(ZoneOffset.UTC).toInstant()
        val duration = Duration.between(timestamp, now)

        val seconds = duration.seconds
        val minutes = duration.toMinutes()
        val hours = duration.toHours()
        val days = duration.toDays()
        val weeks = days / 7

        return when {
            minutes < 1 -> "$seconds second${if (seconds == 1L) "" else "s"} ago"
            hours < 1 -> "$minutes minute${if (minutes == 1L) "" else "s"} ago"
            days < 1 -> "$hours hour${if (hours == 1L) "" else "s"} ago"
            days < 7 -> "$days day${if (days == 1L) "" else "s"} ago"
            else -> "$weeks week${if (weeks == 1L) "" else "s"} ago"
        }
    }
}