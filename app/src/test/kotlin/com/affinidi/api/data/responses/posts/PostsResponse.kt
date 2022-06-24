package com.affinidi.api.data.responses.posts


data class PostsResponse(
    val body: String,
    val id: Int,
    val title: String,
    val userId: Int
)
