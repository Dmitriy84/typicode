package com.affinidi.api.data.responses.photos


data class PhotosResponse(
    val id: Int,
    val title: String,
    val albumId: Int,
    val url: String,
    val thumbnailUrl: String
)
