package com.affinidi.api.steps.responses


data class PhotosResponse(
    val id: Int,
    val title: String,
    val albumId: Int,
    val url: String,
    val thumbnailUrl: String
)
