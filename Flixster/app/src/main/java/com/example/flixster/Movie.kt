package com.example.flixster

import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("overview") val overview: String,
    @SerializedName("poster_path") val posterPath: String,
    @SerializedName("status") val status: String,
    @SerializedName("tagline") val tagline: String,
    @SerializedName("genres") val genres: List<Genre>
) {
    val firstGenre: String
        get() = genres.firstOrNull()?.name ?: "N/A"
}

data class Genre(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String
)

