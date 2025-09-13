package com.karrar.movieapp.data.remote.response.actor

import com.google.gson.annotations.SerializedName


data class ActorSocialLinksDto(
    @SerializedName("facebook_id")
    val facebookId: String?,
    @SerializedName("instagram_id")
    val instagramId: String?,
    @SerializedName("twitter_id")
    val twitterId: String?,
    @SerializedName("tiktok_id")
    val tiktokId: String?,
    @SerializedName("youtube_id")
    val youtubeId: String?,
    @SerializedName("imdb_id")
    val imdbId: String?
)