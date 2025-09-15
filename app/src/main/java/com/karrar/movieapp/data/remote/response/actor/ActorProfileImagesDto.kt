package com.karrar.movieapp.data.remote.response.actor

import com.google.gson.annotations.SerializedName

data class ActorProfileImagesDto(
    @SerializedName("profiles")
    val profiles: List<ActorProfileImageDto>
) {
    data class ActorProfileImageDto(
        @SerializedName("file_path")
        val filePath: String?
    )
}

